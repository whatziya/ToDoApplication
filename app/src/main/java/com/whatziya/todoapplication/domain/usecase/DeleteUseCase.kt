package com.whatziya.todoapplication.domain.usecase

import com.whatziya.todoapplication.data.repository.task.TaskRepository
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(id: String): TaskResDto.Delete {
        return repository.delete(id)
    }
}
