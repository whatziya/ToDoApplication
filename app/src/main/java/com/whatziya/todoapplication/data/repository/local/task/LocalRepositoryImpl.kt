package com.whatziya.todoapplication.data.repository.local.task

import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.source.local.TaskLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val localDataSource: TaskLocalDataSource
) : LocalRepository {
    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return localDataSource.getAllTasks()
    }

    override fun getTaskById(id: String): Flow<TaskEntity> {
        return localDataSource.getTaskById(id)
    }

    override suspend fun insertTask(task: TaskEntity) {
        localDataSource.insertTask(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        localDataSource.updateTask(task)
    }

    override suspend fun deleteTaskById(id: String) {
        localDataSource.deleteTaskById(id)
    }


}