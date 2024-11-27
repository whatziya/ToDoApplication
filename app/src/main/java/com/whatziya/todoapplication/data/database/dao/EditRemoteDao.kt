package com.whatziya.todoapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.whatziya.todoapplication.data.database.entity.EditRemoteEntity
import com.whatziya.todoapplication.utils.Constants

@Dao
interface EditRemoteDao {
    @Query("SELECT * FROM ${Constants.EntityName.EDIT_REMOTE}")
    fun getAllRemote(): List<EditRemoteEntity>

    @Insert
    suspend fun insertRemote(task: EditRemoteEntity)

    @Query("DELETE FROM ${Constants.EntityName.EDIT_REMOTE}")
    suspend fun clearAllRemote()
}
