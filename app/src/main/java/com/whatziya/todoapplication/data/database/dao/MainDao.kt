package com.whatziya.todoapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.utils.Constants

@Dao
interface MainDao {
    @Query("SELECT * FROM ${Constants.EntityName.TASKS}")
    fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM ${Constants.EntityName.TASKS} WHERE id=:id")
    fun getTaskById(id: String): TaskEntity

    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM ${Constants.EntityName.TASKS} WHERE id = :id")
    suspend fun deleteTaskById(id: String)
}
