package com.whatziya.todoapplication.domain.usecase

import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.repository.remote.task.RemoteRepository
import com.whatziya.todoapplication.domain.mapper.TaskMapper
import javax.inject.Inject

class AddUseCase @Inject constructor(
    private val repository: RemoteRepository,
    private val mapper: TaskMapper
) {
    suspend operator fun invoke(taskEntity: TaskEntity) {
        val addDto = mapper.toDTO(taskEntity)
        repository.add(TaskReqDto(addDto))
    }
}
