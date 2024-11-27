package com.whatziya.todoapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.whatziya.todoapplication.data.database.entity.DeletedEntity
import com.whatziya.todoapplication.utils.Constants

@Dao
interface DeletedDao {
    @Query("SELECT * FROM ${Constants.EntityName.DELETED}")
    fun getAllDeleted(): List<DeletedEntity>

    @Insert
    suspend fun insertDeleted(task: DeletedEntity)

    @Query("DELETE FROM ${Constants.EntityName.DELETED}")
    suspend fun clearAllDeleted()
}
