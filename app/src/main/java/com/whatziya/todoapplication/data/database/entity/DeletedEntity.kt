package com.whatziya.todoapplication.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.whatziya.todoapplication.utils.Constants

@Entity(tableName = Constants.EntityName.DELETED)
data class DeletedEntity(
    @PrimaryKey
    @ColumnInfo(name = Constants.DeletedEntityName.ID)
    val id: String
)
