package com.whatziya.todoapplication.data.repository.task

import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import com.whatziya.todoapplication.data.source.local.TaskLocalDataSource
import com.whatziya.todoapplication.data.source.remote.TaskRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val localDataSource: TaskLocalDataSource,
    private val remoteDataSource: TaskRemoteDataSource
) : TaskRepository {
    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return localDataSource.getAllTasks()
    }

    override fun getTaskById(id: String): Flow<TaskEntity> {
        return localDataSource.getTaskById(id)
    }

    override suspend fun insertTask(task: TaskEntity) {
        localDataSource.insertTask(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        localDataSource.updateTask(task)
    }

    override suspend fun deleteTaskById(id: String) {
        localDataSource.deleteTaskById(id)
    }

    override suspend fun getAll(): TaskResDto.GetAll {
        return remoteDataSource.getAll()
    }

    override suspend fun add(data: TaskReqDto.AddDto): TaskResDto.Post {
        return remoteDataSource.add(data)
    }

    override suspend fun update(id: String, data: TaskReqDto.UpdateDto): TaskResDto.Update {
        return remoteDataSource.update(id, data)
    }

    override suspend fun delete(id: String): TaskResDto.Delete {
        return remoteDataSource.delete(id)
    }


}