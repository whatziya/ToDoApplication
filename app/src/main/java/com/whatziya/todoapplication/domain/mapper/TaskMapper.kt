package com.whatziya.todoapplication.domain.mapper

import com.whatziya.todoapplication.data.database.entity.TaskEntity
import com.whatziya.todoapplication.data.dto.response.TaskResDto
import com.whatziya.todoapplication.data.dto.request.TaskReqDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskMapper @Inject constructor() : BaseMapper<TaskResDto.TasksItem, TaskEntity> {
    override fun toUIModel(dto: TaskResDto.TasksItem) = dto.run {
        TaskEntity(
            id = id,
            text = text,
            importance = importance.toInt(),
            deadline = deadline,
            isCompleted = done,
            createdAt = createdAt,
            modifiedAt = changedAt
        )
    }

    override fun toDTO(uiModel: TaskEntity) = uiModel.run {
        TaskResDto.TasksItem(
            id = id,
            text = text,
            importance = importance.toString(),
            deadline = deadline,
            done = isCompleted,
            createdAt = createdAt,
            changedAt = modifiedAt ?: createdAt,
            files = null,
            lastUpdatedBy = "Nimrodel",
            color = ""
        )
    }

    fun toEntity(reqDto: TaskReqDto.TaskItem) = reqDto.run {
        TaskEntity(
            id = id,
            text = text,
            importance = importance.toInt(),
            deadline = deadline,
            isCompleted = done,
            createdAt = createdAt,
            modifiedAt = changedAt
        )
    }

    fun toRequestDTO(entity: TaskEntity) = entity.run {
        TaskReqDto.TaskItem(
            id = id,
            text = text,
            importance = importance.toString(),
            deadline = deadline,
            done = isCompleted,
            createdAt = createdAt,
            changedAt = modifiedAt ?: createdAt,
            lastUpdatedBy = "Nimrodel",
            color = ""
        )
    }

    fun toAddRequestDTO(entity: TaskEntity): TaskReqDto.AddDto {
        return TaskReqDto.AddDto(
            status = "ok",
            element = toRequestDTO(entity)
        )
    }

    fun toUpdateRequestDTO(entity: TaskEntity): TaskReqDto.UpdateDto {
        return TaskReqDto.UpdateDto(
            status = "ok",
            element = toRequestDTO(entity)
        )
    }
}
