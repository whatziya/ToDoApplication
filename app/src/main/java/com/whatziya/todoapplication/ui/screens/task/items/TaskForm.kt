package com.whatziya.todoapplication.ui.screens.task.items

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun TaskForm(text: String, onTextChange: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        TaskInputField(
            taskText = text,
            onTextChange = onTextChange
        )
    }
}

@Composable
fun TaskInputField(
    taskText: String,
    onTextChange: (String) -> Unit
) {
    var textState = TextFieldValue(text = taskText)

    BasicTextField(
        value = textState,
        onValueChange = {
            textState = it
            onTextChange(it.text)
        },
        singleLine = false,
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences
        ),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .padding(8.dp),
        decorationBox = { innerTextField ->
            if (textState.text.isEmpty()) {
                Text(
                    text = "Введите текст задачи",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            innerTextField()
        }
    )
}
