package com.whatziya.todoapplication.data.dto.request

import com.google.gson.annotations.SerializedName

sealed interface TaskReqDto{
    data class AddDto(
        val status: String = "ok",
        val element : TaskItem
    ) :TaskReqDto

    data class UpdateDto(
        val status: String = "ok",
        val element : TaskItem
    ) :TaskReqDto

    data class TaskItem(
        val id: String,
        val text: String,
        val importance: String,
        val deadline: Long,
        val done: Boolean,
        val color : String,
        @SerializedName("created_at")val createdAt: Long,
        @SerializedName("changed_at")val changedAt: Long,
        @SerializedName("last_updated_by ")val lastUpdatedBy: String,
    )

}
