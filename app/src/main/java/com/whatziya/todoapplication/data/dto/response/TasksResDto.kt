package com.whatziya.todoapplication.data.dto.response

import com.whatziya.todoapplication.data.dto.TaskItem

data class TasksResDto(
    val status: String? = null,
    val list: List<TaskItem>,
    val revision: Int
)