package com.whatziya.todoapplication.data.source.local

import com.whatziya.todoapplication.data.database.dao.DeletedDao
import com.whatziya.todoapplication.data.database.entity.DeletedEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeletedDataSourceImpl @Inject constructor(
    private val deletedDao: DeletedDao
) {
    fun getAllDeleted(): List<DeletedEntity> {
        return deletedDao.getAllDeleted()
    }
    suspend fun insertDeleted(task: DeletedEntity) {
        deletedDao.insertDeleted(task)
    }
    suspend fun clearAllDeleted() {
        deletedDao.clearAllDeleted()
    }
}
