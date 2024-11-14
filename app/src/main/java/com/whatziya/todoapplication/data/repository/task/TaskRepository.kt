package com.whatziya.todoapplication.data.repository.task

import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskEntity>>
    fun getTaskById(id: String): Flow<TaskEntity>
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTaskById(id: String)
    suspend fun getAll(): TaskResDto.GetAll
    suspend fun add(data : TaskReqDto.AddDto): TaskResDto.Post
    suspend fun update(id: String, data : TaskReqDto.UpdateDto): TaskResDto.Update
    suspend fun delete(id: String): TaskResDto.Delete

}