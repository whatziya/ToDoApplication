package com.whatziya.todoapplication.data.repository.remote.task

import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import com.whatziya.todoapplication.data.dto.response.TasksResDto

interface RemoteRepository {
    suspend fun getAll(): TasksResDto
    suspend fun add(data: TaskReqDto): TaskResDto
    suspend fun update(id: String, data: TaskReqDto): TaskResDto
    suspend fun delete(id: String): TaskResDto
}
