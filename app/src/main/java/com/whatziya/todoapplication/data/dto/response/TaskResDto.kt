package com.whatziya.todoapplication.data.dto.response

import com.google.gson.annotations.SerializedName

sealed interface TaskResDto{
    data class GetAll(
        val status: String,
        val revision : Int,
        val list : List<TasksItem>
    ) : TaskResDto

    data class Post(
        val status: String,
        val revision : Int,
        val element : TasksItem
    ) : TaskResDto

    data class Update(
        val status: String = "ok",
        val revision : Int,
        val element : TasksItem
    ) : TaskResDto

    data class Delete(
        val status: String = "ok",
        val revision : Int,
        val element : TasksItem
    ) : TaskResDto

    data class TasksItem(
        val text: String,
        val files: String?,
        @SerializedName("created_at")val createdAt: Long,
        @SerializedName("changed_at")val changedAt: Long,
        @SerializedName("last_updated_by ")val lastUpdatedBy: String,
        val deadline: Long,
        val id: String,
        val importance: String,
        val done: Boolean,
        val color : String,
    )
}