package com.whatziya.todoapplication.domain.usecase

import com.whatziya.todoapplication.data.repository.remote.task.RemoteRepository
import com.whatziya.todoapplication.domain.mapper.TaskMapper
import javax.inject.Inject

class GetAllUseCase @Inject constructor(
    private val repository: RemoteRepository,
    private val mapper: TaskMapper
) {
    suspend operator fun invoke() = repository.getAll().list.map { item ->
        mapper.toUIModel(item)
    }
}
