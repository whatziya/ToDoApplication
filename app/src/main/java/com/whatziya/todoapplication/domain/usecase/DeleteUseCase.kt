package com.whatziya.todoapplication.domain.usecase

import com.whatziya.todoapplication.data.dto.response.TaskResDto
import com.whatziya.todoapplication.data.repository.remote.task.RemoteRepository
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val repository: RemoteRepository
) {
    suspend operator fun invoke(id: String): TaskResDto {
        return repository.delete(id)
    }
}
