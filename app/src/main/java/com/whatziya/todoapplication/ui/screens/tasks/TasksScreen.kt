package com.whatziya.todoapplication.ui.screens.tasks

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatziya.todoapplication.ui.screens.tasks.items.AddTaskButton
import com.whatziya.todoapplication.ui.screens.tasks.items.TaskList
import com.whatziya.todoapplication.ui.screens.tasks.items.TasksTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    viewModel: TasksViewModel,
    onAddNewTaskButtonClick: () -> Unit,
    onTaskClick: (taskId: String) -> Unit,
) {
    val topAppBarScrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    var areCompletedTasksAreShown by rememberSaveable { mutableStateOf(viewModel.getShowCompletedPreference()) }
    Scaffold(
        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            TasksTopAppBar(
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                completedTasksCount = tasks.filter { it.isCompleted }.size,
                showCompletedTasks = areCompletedTasksAreShown,
                onToggleShowCompleted = {
                    areCompletedTasksAreShown = !areCompletedTasksAreShown
                    viewModel.setShowCompletedPreference(areCompletedTasksAreShown)
                }
            )
        },
        floatingActionButton = {
            AddTaskButton(onClick = onAddNewTaskButtonClick)
        }
    ) { innerPadding ->
        TaskList(
            tasks = tasks,
            showCompletedTasks = areCompletedTasksAreShown,
            onTaskClick = onTaskClick,
            paddingValues = innerPadding,
            onAddNewTaskButtonClick = onAddNewTaskButtonClick,
            onToggleCheckBox = viewModel::updateTask
        )
    }
}
