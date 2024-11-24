package com.whatziya.todoapplication.data.source.local

import com.whatziya.todoapplication.data.database.dao.MainDao
import com.whatziya.todoapplication.data.database.entity.TaskEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskLocalDataSourceImpl @Inject constructor(
    private val mainDao: MainDao
) {
    fun getAllTasks(): List<TaskEntity> {
        return mainDao.getAllTasks()
    }

    fun getTaskById(id: String): TaskEntity {
        return mainDao.getTaskById(id)
    }

    suspend fun insertTask(task: TaskEntity) {
        mainDao.insertTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        mainDao.updateTask(task)
    }

    suspend fun deleteTaskById(id: String) {
        mainDao.deleteTaskById(id)
    }
}
