package com.whatziya.todoapplication.di

import com.whatziya.todoapplication.data.repository.remote.task.RemoteRepository
import com.whatziya.todoapplication.domain.mapper.TaskMapper
import com.whatziya.todoapplication.domain.usecase.AddUseCase
import com.whatziya.todoapplication.domain.usecase.DeleteUseCase
import com.whatziya.todoapplication.domain.usecase.GetAllUseCase
import com.whatziya.todoapplication.domain.usecase.UpdateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetAllUseCase(
        taskRepository: RemoteRepository,
        taskMapper: TaskMapper
    ): GetAllUseCase {
        return GetAllUseCase(taskRepository, taskMapper)
    }

    @Provides
    fun provideAddUseCase(
        taskRepository: RemoteRepository,
        taskMapper: TaskMapper
    ): AddUseCase {
        return AddUseCase(taskRepository, taskMapper)
    }

    @Provides
    fun provideUpdateTaskUseCase(
        taskRepository: RemoteRepository,
        taskMapper: TaskMapper
    ): UpdateUseCase {
        return UpdateUseCase(taskRepository, taskMapper)
    }

    @Provides
    fun provideDeleteUseCase(
        taskRepository: RemoteRepository
    ): DeleteUseCase {
        return DeleteUseCase(taskRepository)
    }
}