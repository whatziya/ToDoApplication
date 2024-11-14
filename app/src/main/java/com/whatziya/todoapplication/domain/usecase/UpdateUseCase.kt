package com.whatziya.todoapplication.domain.usecase

import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.repository.task.TaskRepository
import com.whatziya.todoapplication.domain.mapper.TaskMapper
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val mapper: TaskMapper
) {
    suspend operator fun invoke(taskEntity: TaskEntity, taskId: String) {
        val updateDto = mapper.toUpdateRequestDTO(taskEntity)
        repository.update(taskId, updateDto)
    }
}
