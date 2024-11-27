package com.whatziya.todoapplication.domain.mapper

import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.dto.common.TaskItem

fun TaskItem.toUIModel() = this.run {
    TaskEntity(
        id = id,
        text = text,
        importance = when (importance) {
            "low" -> 1
            "basic" -> 0
            else -> 2
        },
        deadline = deadline,
        isCompleted = done,
        createdAt = createdAt,
        modifiedAt = changedAt
    )
}

fun TaskEntity.toDTO() = this.run {
    TaskItem(
        id = id,
        text = text,
        importance = when (importance) {
            1 -> "low"
            0 -> "basic"
            else -> "important"
        },
        deadline = deadline,
        done = isCompleted,
        createdAt = createdAt,
        changedAt = modifiedAt ?: createdAt,
        lastUpdatedBy = "Nimrodel",
        color = ""
    )
}
