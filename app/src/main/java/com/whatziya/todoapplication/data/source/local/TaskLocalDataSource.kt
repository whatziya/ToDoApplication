package com.whatziya.todoapplication.data.source.local

import com.whatziya.todoapplication.data.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {
    fun getAllTasks(): Flow<List<TaskEntity>>
    fun getTaskById(id: String): Flow<TaskEntity>
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTaskById(id: String)
}
