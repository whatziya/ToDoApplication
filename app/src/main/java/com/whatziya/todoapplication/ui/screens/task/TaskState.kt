package com.whatziya.todoapplication.ui.screens.task

import com.whatziya.todoapplication.data.database.entity.TaskEntity

sealed interface TaskState {
    data class NewTask(val task: TaskEntity) : TaskState

    data class EditTask(val task: TaskEntity) : TaskState

    data object None : TaskState

    data object GoBack : TaskState
}
