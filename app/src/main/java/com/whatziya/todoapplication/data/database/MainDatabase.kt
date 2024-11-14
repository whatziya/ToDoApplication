package com.whatziya.todoapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.whatziya.todoapplication.data.database.dao.MainDao
import com.whatziya.todoapplication.data.database.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDao() : MainDao
}