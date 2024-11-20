package com.whatziya.todoapplication.domain.usecase

import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import com.whatziya.todoapplication.data.repository.remote.task.RemoteRepository
import com.whatziya.todoapplication.domain.mapper.TaskMapper
import javax.inject.Inject

class UpdateUseCase @Inject constructor(
    private val repository: RemoteRepository,
    private val mapper: TaskMapper
) {
    suspend operator fun invoke(taskEntity: TaskEntity, taskId: String) {
        val updateDto = mapper.toDTO(taskEntity)
        repository.update(taskId, TaskReqDto(updateDto))
    }
}
