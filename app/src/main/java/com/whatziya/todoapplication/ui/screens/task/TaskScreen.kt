package com.whatziya.todoapplication.ui.screens.task

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatziya.todoapplication.ui.screens.task.items.DeadlineSelector
import com.whatziya.todoapplication.ui.screens.task.items.DeleteTaskButton
import com.whatziya.todoapplication.ui.screens.task.items.ImportanceSelector
import com.whatziya.todoapplication.ui.screens.task.items.TaskForm
import com.whatziya.todoapplication.ui.screens.task.items.TaskTopAppBar

sealed class TaskScreenMode {
    data object NewTask : TaskScreenMode()
    data object EditTask : TaskScreenMode()
}

@Composable
fun TaskScreen(
    viewModel: TaskViewModel,
    taskId: String,
    onExit: () -> Unit
) {
    LaunchedEffect(taskId) {
        viewModel.getTaskById(taskId)
    }

    val task by viewModel.task.collectAsStateWithLifecycle()

    val taskMode =
        if (taskId.isEmpty()) TaskScreenMode.NewTask else TaskScreenMode.EditTask

    println(taskMode)
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskTopAppBar(
                onExit = onExit,
                onSaveTask = {
                    if (task?.text?.isEmpty() == true) {
                        Toast.makeText(context, "Введите текст задачи", Toast.LENGTH_SHORT).show()
                    } else {
                        when (taskMode) {
                            is TaskScreenMode.NewTask -> {
                                viewModel.insertTask()
                                onExit()
                            }

                            is TaskScreenMode.EditTask -> {
                                viewModel.updateTask()
                                onExit()
                            }
                        }
                    }
                }
            )
        },
        content = { innerPadding ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                TaskForm(
                    text = task?.text ?: "",
                    onTextChange = {
                        viewModel.updateText(it)
                    }
                )
                ImportanceSelector(
                    selectedImportance = task?.importance ?: 0,
                    onImportanceChange = { viewModel.updateImportance(it) }
                )
                Spacer(Modifier.padding(vertical = 4.dp))
                DeadlineSelector(
                    selectedDate = if (taskId.isEmpty()) 0L else task?.deadline ?: 0L,
                    isSwitchChecked = if (taskId.isEmpty()) false else task?.deadline != null,
                    onDeadlineChanged = { date ->
                        viewModel.updateDeadline(date)
                    }
                )
                Spacer(Modifier.padding(vertical = 4.dp))
                if (taskId.isNotEmpty()) {
                    DeleteTaskButton(onDelete = {
                        viewModel.deleteTask(taskId)
                        onExit()
                    })
                }
            }
        }
    )
}
