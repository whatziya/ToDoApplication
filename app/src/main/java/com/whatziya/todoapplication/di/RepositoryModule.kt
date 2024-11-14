package com.whatziya.todoapplication.di

import com.whatziya.todoapplication.data.repository.task.TaskRepository
import com.whatziya.todoapplication.data.repository.task.TaskRepositoryImpl
import com.whatziya.todoapplication.data.source.local.TaskLocalDataSource
import com.whatziya.todoapplication.data.source.remote.TaskRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideTaskRepository(
        localDataSource: TaskLocalDataSource,
        remoteDataSource: TaskRemoteDataSource
    ): TaskRepository {
        return TaskRepositoryImpl(localDataSource, remoteDataSource)
    }
}