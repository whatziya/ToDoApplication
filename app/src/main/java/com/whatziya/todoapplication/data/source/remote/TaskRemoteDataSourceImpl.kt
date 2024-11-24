package com.whatziya.todoapplication.data.source.remote

import com.whatziya.todoapplication.data.api.TasksApi
import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import com.whatziya.todoapplication.data.dto.response.TasksResDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRemoteDataSourceImpl @Inject constructor(
    private val tasksService: TasksApi
) {
    suspend fun getAll(): Result<TasksResDto> = withContext(Dispatchers.IO) {
        val response = tasksService.getAll()
        if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!)
        } else if (response.errorBody() != null) {
            Result.failure(Exception(response.message()))
        } else {
            Result.failure(Throwable("Xato"))
        }
    }

    suspend fun add(data: TaskReqDto): Result<TaskResDto> = withContext(Dispatchers.IO) {
        val response = tasksService.add(data = data)
        if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!)
        } else if (response.errorBody() != null) {
            Result.failure(Exception(response.message()))
        } else {
            Result.failure(Throwable("Xato"))
        }
    }

    suspend fun update(id: String, data: TaskReqDto): Result<TaskResDto> = withContext(Dispatchers.IO) {
        val response = tasksService.update(id, data)
        if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!)
        } else if (response.errorBody() != null) {
            Result.failure(Exception(response.message()))
        } else {
            Result.failure(Throwable("Xato"))
        }
    }

    suspend fun delete(id: String): Result<TaskResDto> = withContext(Dispatchers.IO) {
        val response = tasksService.delete(id)
        if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!)
        } else if (response.errorBody() != null) {
            Result.failure(Exception(response.message()))
        } else {
            Result.failure(Throwable("Xato"))
        }
    }
}
