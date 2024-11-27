package com.whatziya.todoapplication.ui.screens.tasks.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
