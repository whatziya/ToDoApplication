package com.whatziya.todoapplication.di

import com.whatziya.todoapplication.data.api.TasksService
import com.whatziya.todoapplication.data.database.dao.MainDao
import com.whatziya.todoapplication.data.source.local.TaskLocalDataSource
import com.whatziya.todoapplication.data.source.local.TaskLocalDataSourceImpl
import com.whatziya.todoapplication.data.source.remote.TaskRemoteDataSource
import com.whatziya.todoapplication.data.source.remote.TaskRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SourceModule {
    @Singleton
    @Provides
    fun provideTaskLocalDataSource(
        dao: MainDao
    ): TaskLocalDataSource {
        return TaskLocalDataSourceImpl(dao)
    }

    @Singleton
    @Provides
    fun provideTaskRemoteDataSource(
        service: TasksService
    ): TaskRemoteDataSource {
        return TaskRemoteDataSourceImpl(service)
    }
}