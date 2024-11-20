package com.whatziya.todoapplication.data.dto.response

import com.whatziya.todoapplication.data.dto.TaskItem

data class TaskResDto(
    val status: String? = null,
    val element: TaskItem,
    val revision: Int
)
