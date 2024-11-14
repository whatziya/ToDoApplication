package com.whatziya.todoapplication.di

import com.whatziya.todoapplication.data.repository.task.TaskRepository
import com.whatziya.todoapplication.domain.mapper.TaskMapper
import com.whatziya.todoapplication.domain.usecase.AddUseCase
import com.whatziya.todoapplication.domain.usecase.DeleteUseCase
import com.whatziya.todoapplication.domain.usecase.GetAllUseCase
import com.whatziya.todoapplication.domain.usecase.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetAllUseCase(
        taskRepository: TaskRepository,
        taskMapper: TaskMapper
    ): GetAllUseCase {
        return GetAllUseCase(taskRepository, taskMapper)
    }

    @Provides
    fun provideAddUseCase(
        taskRepository: TaskRepository,
        taskMapper: TaskMapper
    ): AddUseCase {
        return AddUseCase(taskRepository, taskMapper)
    }

    @Provides
    fun provideUpdateTaskUseCase(
        taskRepository: TaskRepository,
        taskMapper: TaskMapper
    ): UpdateTaskUseCase {
        return UpdateTaskUseCase(taskRepository, taskMapper)
    }

    @Provides
    fun provideDeleteUseCase(
        taskRepository: TaskRepository
    ): DeleteUseCase {
        return DeleteUseCase(taskRepository)
    }
}