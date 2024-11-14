package com.whatziya.todoapplication.data.source.remote

import com.whatziya.todoapplication.data.api.TasksService
import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import javax.inject.Inject

class TaskRemoteDataSourceImpl @Inject constructor(
    private val tasksService: TasksService
) : TaskRemoteDataSource {
    override suspend fun getAll(): TaskResDto.GetAll {
        return tasksService.getAll().body() ?: throw Exception("No body")
    }

    override suspend fun add(data: TaskReqDto.AddDto): TaskResDto.Post {
        return tasksService.add(data).body() ?: throw Exception("No body")
    }

    override suspend fun update(id: String, data: TaskReqDto.UpdateDto): TaskResDto.Update {
        return tasksService.update(id, data).body() ?: throw Exception("No body")
    }

    override suspend fun delete(id: String): TaskResDto.Delete {
        return tasksService.delete(id).body() ?: throw Exception("No body")
    }

}