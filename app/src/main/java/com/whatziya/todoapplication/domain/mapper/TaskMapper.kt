package com.whatziya.todoapplication.domain.mapper

import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.dto.TaskItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskMapper @Inject constructor() : BaseMapper<TaskItem, TaskEntity> {
    override fun toUIModel(dto: TaskItem) = dto.run {
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

    override fun toDTO(uiModel: TaskEntity) = uiModel.run {
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
}
