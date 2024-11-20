package com.whatziya.todoapplication.di

import com.whatziya.todoapplication.data.repository.local.task.LocalRepository
import com.whatziya.todoapplication.data.repository.local.task.LocalRepositoryImpl
import com.whatziya.todoapplication.data.repository.remote.task.RemoteRepository
import com.whatziya.todoapplication.data.repository.remote.task.RemoteRepositoryImpl
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
    fun provideLocalRepository(
        localDataSource: TaskLocalDataSource
    ): LocalRepository {
        return LocalRepositoryImpl(localDataSource)
    }

    @Singleton
    @Provides
    fun provideRemoteRepository(
        remoteDataSource: TaskRemoteDataSource
    ): RemoteRepository {
        return RemoteRepositoryImpl(remoteDataSource)
    }
}
