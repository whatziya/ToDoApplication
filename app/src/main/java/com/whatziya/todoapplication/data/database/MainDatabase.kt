package com.whatziya.todoapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whatziya.todoapplication.data.database.dao.DeletedDao
import com.whatziya.todoapplication.data.database.dao.EditRemoteDao
import com.whatziya.todoapplication.data.database.dao.MainDao
import com.whatziya.todoapplication.data.database.entity.DeletedEntity
import com.whatziya.todoapplication.data.database.entity.EditRemoteEntity
import com.whatziya.todoapplication.data.database.entity.TaskEntity

@Database(entities = [TaskEntity::class, DeletedEntity::class, EditRemoteEntity::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao
    abstract fun editRemoteDao(): EditRemoteDao
    abstract fun deletedDao(): DeletedDao
}
