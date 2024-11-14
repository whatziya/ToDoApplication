package com.whatziya.todoapplication.domain.usecase

import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.repository.task.TaskRepository
import com.whatziya.todoapplication.domain.mapper.TaskMapper
import javax.inject.Inject

class AddUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val mapper: TaskMapper
) {
    suspend operator fun invoke(taskEntity: TaskEntity) {
        val addDto = mapper.toAddRequestDTO(taskEntity)
        repository.add(addDto)
    }
}
