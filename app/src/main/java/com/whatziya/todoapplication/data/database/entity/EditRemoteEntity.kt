package com.whatziya.todoapplication.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.whatziya.todoapplication.utils.Constants

@Entity(tableName = Constants.EntityName.EDIT_REMOTE)
data class EditRemoteEntity(
    @PrimaryKey
    @ColumnInfo(name = Constants.EditRemoteEntityName.ID)
    val id: String,
    @ColumnInfo(name = Constants.EditRemoteEntityName.ACTION)
    val text: String
)
