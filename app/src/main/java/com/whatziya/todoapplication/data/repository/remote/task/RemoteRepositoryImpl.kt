package com.whatziya.todoapplication.data.repository.remote.task

import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import com.whatziya.todoapplication.data.dto.response.TasksResDto
import com.whatziya.todoapplication.data.source.remote.TaskRemoteDataSource
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remoteDataSource: TaskRemoteDataSource
) : RemoteRepository {
    override suspend fun getAll(): TasksResDto {
        return remoteDataSource.getAll()
    }

    override suspend fun add(data: TaskReqDto): TaskResDto {
        return remoteDataSource.add(data)
    }

    override suspend fun update(id: String, data: TaskReqDto): TaskResDto {
        return remoteDataSource.update(id, data)
    }

    override suspend fun delete(id: String): TaskResDto {
        return remoteDataSource.delete(id)
    }
}