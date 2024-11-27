package com.whatziya.todoapplication.data.dto.request

import com.google.gson.annotations.SerializedName
import com.whatziya.todoapplication.data.dto.common.TaskItem

data class TaskReqDto(
    @field:SerializedName("element")
    val element: TaskItem
)
