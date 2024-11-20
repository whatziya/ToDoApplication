package com.whatziya.todoapplication.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.whatziya.todoapplication.utils.Constants

@Entity(tableName = Constants.EntityName.TASKS)
data class TaskEntity(
    @PrimaryKey
    @ColumnInfo(name = Constants.TaskEntityName.ID)
    val id : String,
    @ColumnInfo(name = Constants.TaskEntityName.TEXT)
    var text : String,
    @ColumnInfo(name = Constants.TaskEntityName.IMPORTANCE)
    val importance : Int,
    @ColumnInfo(name = Constants.TaskEntityName.DEADLINE)
    val deadline : Long?,
    @ColumnInfo(name = Constants.TaskEntityName.IS_COMPLETED)
    val isCompleted : Boolean,
    @ColumnInfo(name = Constants.TaskEntityName.CREATED_AT)
    val createdAt : Long,
    @ColumnInfo(name = Constants.TaskEntityName.MODIFIED_AT)
    var modifiedAt : Long?
)
