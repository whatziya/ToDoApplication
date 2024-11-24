package com.whatziya.todoapplication.data.repository

import com.whatziya.todoapplication.data.NetworkState
import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import com.whatziya.todoapplication.data.dto.response.TasksResDto
import com.whatziya.todoapplication.data.source.local.TaskLocalDataSourceImpl
import com.whatziya.todoapplication.data.source.remote.TaskRemoteDataSourceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val localDataSource: TaskLocalDataSourceImpl,
    private val remoteDataSource: TaskRemoteDataSourceImpl,
    private val networkState: NetworkState
) {
    fun _getAll(): List<TaskEntity> {
        return localDataSource.getAllTasks()
    }

    fun _getTaskById(id: String): TaskEntity {
        return localDataSource.getTaskById(id)
    }

    suspend fun _add(task: TaskEntity) {
        localDataSource.insertTask(task)
    }

    suspend fun _update(task: TaskEntity) {
        localDataSource.updateTask(task)
    }

    suspend fun _delete(id: String) {
        localDataSource.deleteTaskById(id)
    }

    fun getAll(): Flow<TasksResDto> = flow {
        val result = remoteDataSource.getAll()
        result.onSuccess {
            emit(it)
        }.onFailure {

        }
    }

    fun add(data: TaskReqDto): Flow<TaskResDto> = flow {
        val result = remoteDataSource.add(data)
        result.onSuccess {
            emit(it)
        }.onFailure {

        }
    }

    fun update(id: String, data: TaskReqDto): Flow<TaskResDto> = flow {
        val result = remoteDataSource.update(id, data)
        result.onSuccess {
            emit(it)
        }.onFailure {

        }
    }

    fun delete(id: String): Flow<TaskResDto> = flow {
        val result = remoteDataSource.delete(id)
        result.onSuccess {
            emit(it)
        }.onFailure {

        }
    }
}