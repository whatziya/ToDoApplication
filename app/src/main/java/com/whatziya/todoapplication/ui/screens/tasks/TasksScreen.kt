package com.whatziya.todoapplication.ui.screens.tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.whatziya.todoapplication.R
import com.whatziya.todoapplication.data.database.entity.TaskEntity
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    viewModel: TasksViewModel,
    onAddNewTaskButtonClick: () -> Unit,
    onTaskClick: (taskId: String) -> Unit,
) {
    val topAppBarScrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val tasks by viewModel.tasks.collectAsState()
    var areCompletedTasksAreShown by rememberSaveable { mutableStateOf(viewModel.getShowCompletedPreference()) }
    Scaffold(
        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            TasksTopAppBar(topAppBarScrollBehavior = topAppBarScrollBehavior,
                completedTasksCount = tasks.filter { it.isCompleted }.size,
                showCompletedTasks = areCompletedTasksAreShown,
                onToggleShowCompleted = {
                    areCompletedTasksAreShown = !areCompletedTasksAreShown
                    viewModel.setShowCompletedPreference(areCompletedTasksAreShown)
                })
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

@Composable
fun AddTaskButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            tint = Color.White,
            contentDescription = null,
        )
    }
}

@Composable
fun TaskList(
    tasks: List<TaskEntity>,
    showCompletedTasks: Boolean,
    onTaskClick: (taskId: String) -> Unit,
    paddingValues: PaddingValues,
    onAddNewTaskButtonClick: () -> Unit,
    onToggleCheckBox: (TaskEntity) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        LazyColumn {
            item { Spacer(modifier = Modifier.padding(top = 16.dp)) }
            val filteredItems =
                if (showCompletedTasks) tasks else tasks.filter { !it.isCompleted }
            items(filteredItems.size) { item ->
                TaskContainer(
                    taskItem = filteredItems[item],
                    onClick = { onTaskClick(it) },
                    onToggleCheckBox = onToggleCheckBox
                )
            }
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .clickable { onAddNewTaskButtonClick() }
                        .padding(start = 56.dp, top = 14.dp, bottom = 24.dp)
                ) {
                    Text(
                        "Новое дело",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }
    }
}

@Composable
fun TaskContainer(taskItem: TaskEntity, onClick: (taskId: String) -> Unit, onToggleCheckBox: (TaskEntity) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick(taskItem.id) }
            .fillMaxWidth()
            .height(72.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = taskItem.isCompleted,
            onCheckedChange = {
                onToggleCheckBox(taskItem)
            },
            enabled = !taskItem.isCompleted,
            colors = if (taskItem.isCompleted) {
                CheckboxColors(
                    checkedCheckmarkColor = Color.White,
                    uncheckedCheckmarkColor = Color.Transparent,
                    checkedBoxColor = MaterialTheme.colorScheme.secondary,
                    uncheckedBoxColor = Color.Transparent,
                    disabledCheckedBoxColor = MaterialTheme.colorScheme.secondary,
                    disabledUncheckedBoxColor = Color.Transparent,
                    disabledIndeterminateBoxColor = Color.Transparent,
                    checkedBorderColor = MaterialTheme.colorScheme.secondary,
                    uncheckedBorderColor = Color.Transparent,
                    disabledBorderColor = MaterialTheme.colorScheme.secondary,
                    disabledUncheckedBorderColor = Color.Transparent,
                    disabledIndeterminateBorderColor = Color.Transparent
                )
            } else if (taskItem.importance == 0 || taskItem.importance == 1) {
                CheckboxColors(
                    checkedCheckmarkColor = Color.Transparent,
                    uncheckedCheckmarkColor = Color.Transparent,
                    checkedBoxColor = Color.Transparent,
                    uncheckedBoxColor = Color.Transparent,
                    disabledCheckedBoxColor = Color.Transparent,
                    disabledUncheckedBoxColor = Color.Transparent,
                    disabledIndeterminateBoxColor = Color.Transparent,
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = MaterialTheme.colorScheme.onBackground,
                    disabledBorderColor = Color.Transparent,
                    disabledUncheckedBorderColor = Color.Transparent,
                    disabledIndeterminateBorderColor = Color.Transparent
                )
            } else {
                CheckboxColors(
                    checkedCheckmarkColor = Color.Transparent,
                    uncheckedCheckmarkColor = Color.Transparent,
                    checkedBoxColor = Color.Transparent,
                    uncheckedBoxColor = Color(0xFFFF7B70),
                    disabledCheckedBoxColor = Color.Transparent,
                    disabledUncheckedBoxColor = Color.Transparent,
                    disabledIndeterminateBoxColor = Color.Transparent,
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = MaterialTheme.colorScheme.error,
                    disabledBorderColor = Color.Transparent,
                    disabledUncheckedBorderColor = Color.Transparent,
                    disabledIndeterminateBorderColor = Color.Transparent
                )
            }
        )

        Row(
            Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
                .weight(1f), verticalAlignment = Alignment.CenterVertically
        ) {
            if (taskItem.importance != 0) {
                Image(
                    modifier = Modifier.padding(end = 8.dp),
                    contentDescription = null,
                    painter = if (taskItem.importance == 1) painterResource(R.drawable.ic_low) else painterResource(
                        R.drawable.ic_important
                    )
                )
            }
            Column {
                Text(
                    text = taskItem.text,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (taskItem.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (taskItem.isCompleted) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text =
                    SimpleDateFormat(
                        "dd.MM.yyyy",
                        Locale.getDefault()
                    ).format(taskItem.modifiedAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = "Info icon",
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksTopAppBar(
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    completedTasksCount: Int,
    showCompletedTasks: Boolean,
    onToggleShowCompleted: () -> Unit
) {
    val topAppBarExpanded = topAppBarScrollBehavior.state.collapsedFraction == 0f
    val topAppBarCollapsed = topAppBarScrollBehavior.state.collapsedFraction == 1f

    Surface(
        shadowElevation = if (topAppBarCollapsed) 4.dp else 0.dp
    ) {
        LargeTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            title = {
                Row {
                    Column(
                        Modifier
                            .padding(start = 56.dp)
                            .weight(1f)
                    ) {
                        Text(
                            "Мои дела",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleLarge
                        )
                        if (topAppBarExpanded) {
                            Text(
                                "Выполнено - $completedTasksCount",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        }
                    }
                    if (topAppBarExpanded) {
                        IconButton(onClick = onToggleShowCompleted, Modifier.padding(end = 8.dp)) {
                            Icon(
                                painter = if (showCompletedTasks) painterResource(R.drawable.ic_invisible) else painterResource(
                                    R.drawable.ic_visible
                                ),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            },
            actions = {
                if (topAppBarCollapsed) {
                    IconButton(onClick = onToggleShowCompleted) {
                        Icon(
                            painter = if (showCompletedTasks) painterResource(R.drawable.ic_invisible) else painterResource(
                                R.drawable.ic_visible
                            ),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            scrollBehavior = topAppBarScrollBehavior
        )
    }
}
