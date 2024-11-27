package com.whatziya.todoapplication.ui.screens.tasks.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatziya.todoapplication.data.database.entity.TaskEntity
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
