package com.whatziya.todoapplication.data.source.local

import com.whatziya.todoapplication.data.database.dao.MainDao
import com.whatziya.todoapplication.data.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskLocalDataSourceImpl @Inject constructor(
    private val mainDao: MainDao
) : TaskLocalDataSource {
    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return mainDao.getAllTasks()
    }

    override fun getTaskById(id: String): Flow<TaskEntity> {
        return mainDao.getTaskById(id)
    }

    override suspend fun insertTask(task: TaskEntity) {
        mainDao.insertTask(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        mainDao.updateTask(task)
    }

    override suspend fun deleteTaskById(id: String) {
        mainDao.deleteTaskById(id)
    }
}