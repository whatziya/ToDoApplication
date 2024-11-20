package com.whatziya.todoapplication.data.source.remote

import com.whatziya.todoapplication.data.api.TasksApi
import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import com.whatziya.todoapplication.data.dto.response.TasksResDto
import javax.inject.Inject

class TaskRemoteDataSourceImpl @Inject constructor(
    private val tasksService: TasksApi
) : TaskRemoteDataSource {
    override suspend fun getAll(): TasksResDto {
        return tasksService.getAll()
    }

    override suspend fun add(data: TaskReqDto): TaskResDto {
        return tasksService.add(
            data = data,
            revision = Revision.value
        )
    }

    override suspend fun update(id: String, data: TaskReqDto): TaskResDto {
        return tasksService.update(id, Revision.value, data)
    }

    override suspend fun delete(id: String): TaskResDto {
        return tasksService.delete(id, Revision.value)
    }
}
