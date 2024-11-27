package com.whatziya.todoapplication.data.repository

import com.whatziya.todoapplication.data.NetworkState
import com.whatziya.todoapplication.data.database.entity.DeletedEntity
import com.whatziya.todoapplication.data.database.entity.EditRemoteEntity
import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.dto.common.TaskItem
import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import com.whatziya.todoapplication.data.source.common.Revision
import com.whatziya.todoapplication.data.source.local.DeletedDataSourceImpl
import com.whatziya.todoapplication.data.source.local.EditRemoteDataSourceImpl
import com.whatziya.todoapplication.data.source.local.TaskLocalDataSourceImpl
import com.whatziya.todoapplication.data.source.remote.TaskRemoteDataSourceImpl
import com.whatziya.todoapplication.domain.mapper.toDTO
import com.whatziya.todoapplication.domain.mapper.toUIModel
import com.whatziya.todoapplication.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val localDataSource: TaskLocalDataSourceImpl,
    private val remoteDataSource: TaskRemoteDataSourceImpl,
    private val deletedDataSource: DeletedDataSourceImpl,
    private val editRemoteDataSource: EditRemoteDataSourceImpl,
    private val networkState: NetworkState
) {

    private fun _getAll(): List<TaskEntity> {
        return localDataSource.getAll()
    }

    fun _getTaskById(id: String): TaskEntity {
        return localDataSource.getTaskById(id)
    }

    private suspend fun _add(task: TaskEntity) {
        localDataSource.insertTask(task)
    }

    private suspend fun _update(task: TaskEntity) {
        localDataSource.updateTask(task)
    }

    private suspend fun _delete(id: String) {
        localDataSource.deleteTaskById(id)
    }

    private suspend fun _deleteAll() {
        localDataSource.deleteAllTasks()
    }

    fun getAll(): Flow<List<TaskEntity>> = flow {
        if (isNetworkAvailable()) {
            try {
                syncOfflineChanges()
                syncOnlineChanges()

                val result = remoteDataSource.getAll()
                result.onSuccess { serverResponse ->
                    emit(serverResponse.list.map { it.toUIModel() })
                    Revision.value = serverResponse.revision
                }.onFailure {
                    emit(emptyList())
                }
            } catch (e: Exception) {

                emit(emptyList())
            }
        } else {
            val localTasks = withContext(Dispatchers.IO) { _getAll() }
            emit(localTasks)
        }
    }

    fun add(data: TaskReqDto): Flow<TaskResDto?> = flow {

        val task = data.element.toUIModel()

        if (isNetworkAvailable()) {

            val result = remoteDataSource.add(data)
            result.onSuccess { response ->
                emit(response)
                withContext(Dispatchers.IO) {
                    _add(task)
                    Revision.value = response.revision
                }
            }.onFailure {
                emit(null)
            }
        } else {
            withContext(Dispatchers.IO) {
                _add(task)
                editRemoteDataSource.insertRemote(
                    EditRemoteEntity(task.id, Constants.EditRemoteAction.ADD)
                )
            }
            emit(null)
        }
    }.flowOn(Dispatchers.IO)

    fun update(id: String, data: TaskReqDto): Flow<TaskResDto?> = flow {
        val task = data.element.toUIModel()

        if (isNetworkAvailable()) {
            val result = remoteDataSource.update(id, data)
            result.onSuccess { response ->
                emit(response)
                withContext(Dispatchers.IO) {
                    _update(task)
                    Revision.value = response.revision
                }
            }.onFailure {
                emit(null)
            }
        } else {
            withContext(Dispatchers.IO) {
                _update(task)
                editRemoteDataSource.insertRemote(
                    EditRemoteEntity(
                        task.id,
                        Constants.EditRemoteAction.UPDATE
                    )
                )
            }
            emit(null)
        }
    }.flowOn(Dispatchers.IO)

    fun delete(id: String): Flow<TaskResDto?> = flow {
        if (isNetworkAvailable()) {
            val result = remoteDataSource.delete(id)
            result.onSuccess { response ->
                emit(response)
                withContext(Dispatchers.IO) {
                    _delete(id)
                    Revision.value = response.revision
                }
            }.onFailure {
                emit(null)
            }
        } else {
            withContext(Dispatchers.IO) {
                _delete(id)
                deletedDataSource.insertDeleted(DeletedEntity(id))
            }
            emit(null)
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun syncOfflineChanges() {
        // Sync for added tasks
        val addedTasks =
            editRemoteDataSource.getAllRemote().filter { it.text == Constants.EditRemoteAction.ADD }
        for (task in addedTasks) {
            add(
                TaskReqDto(
                    withContext(Dispatchers.IO) { _getTaskById(task.id).toDTO() }
                )
            )
        }

        // Sync for updated tasks
        val updatedTasks = editRemoteDataSource.getAllRemote()
            .filter { it.text == Constants.EditRemoteAction.UPDATE }
        for (task in updatedTasks) {
            update(
                task.id,
                TaskReqDto(
                    withContext(Dispatchers.IO) { _getTaskById(task.id).toDTO() }
                )
            )
        }

        // Sync for deleted tasks
        val deletedTasks = deletedDataSource.getAllDeleted()
        for (task in deletedTasks) {
            delete(task.id)
        }
        editRemoteDataSource.clearAllRemote()
        deletedDataSource.clearAllDeleted()
    }

    private suspend fun syncOnlineChanges() {
        var serverTasks: List<TaskItem> = emptyList()
        remoteDataSource.getAll().onSuccess {
            serverTasks = it.list
        }.onFailure {
            // log smth
        }
        val localTasks = withContext(Dispatchers.IO) { _getAll() }

        val serverTaskMap = serverTasks.associateBy { it.id }
        val localTaskMap = localTasks.associateBy { it.id }

        for ((id, serverTask) in serverTaskMap) {
            val localTask = localTaskMap[id]
            if (localTask == null) {
                withContext(Dispatchers.IO) {
                    _add(serverTask.toUIModel())
                }
            } else if ((serverTask.changedAt ?: 0) > (localTask.modifiedAt ?: 0)) {
                withContext(Dispatchers.IO) {
                    _update(serverTask.toUIModel())
                }
            }
        }

        val tasksToDelete = localTasks.filter { it.id !in serverTaskMap.keys }
        withContext(Dispatchers.IO) {
            tasksToDelete.forEach { _delete(it.id) }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        return networkState.value ?: false
    }
}
