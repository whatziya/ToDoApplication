package com.whatziya.todoapplication.data.source.remote

import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.dto.response.TaskResDto

interface TaskRemoteDataSource {
    suspend fun getAll(): TaskResDto.GetAll
    suspend fun add(data : TaskReqDto.AddDto): TaskResDto.Post
    suspend fun update(id: String, data : TaskReqDto.UpdateDto): TaskResDto.Update
    suspend fun delete(id: String): TaskResDto.Delete
}