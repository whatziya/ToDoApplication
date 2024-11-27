package com.whatziya.todoapplication.data.source.local

import com.whatziya.todoapplication.data.database.dao.EditRemoteDao
import com.whatziya.todoapplication.data.database.entity.EditRemoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditRemoteDataSourceImpl @Inject constructor(
    private val newRemoteDao: EditRemoteDao
) {
    fun getAllRemote(): List<EditRemoteEntity> {
        return newRemoteDao.getAllRemote()
    }
    suspend fun insertRemote(task: EditRemoteEntity) {
        newRemoteDao.insertRemote(task)
    }
    suspend fun clearAllRemote() {
        newRemoteDao.clearAllRemote()
    }
}
