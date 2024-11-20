package com.whatziya.todoapplication.data.dto

import com.google.gson.annotations.SerializedName

data class TaskItem(
    val id: String,
    @SerializedName("last_updated_by ") val lastUpdatedBy: String = "",
    val color: String? = null,
    val importance: String,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("changed_at") val changedAt: Long? = null,
    val text: String,
    val deadline: Long? = null,
    val done: Boolean,
)
