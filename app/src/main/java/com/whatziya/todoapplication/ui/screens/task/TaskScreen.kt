package com.whatziya.todoapplication.ui.screens.task

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whatziya.todoapplication.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

    val task by viewModel.task.collectAsState()

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

@Composable
fun DeleteTaskButton(
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .height(72.dp)
            .fillMaxWidth()
            .clickable { onDelete() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            "Удалить",
            modifier = Modifier.padding(horizontal = 4.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun DeadlineSelector(
    selectedDate: Long,
    isSwitchChecked: Boolean,
    onDeadlineChanged: (Long?) -> Unit
) {
    val isSwitchCheck = remember { mutableStateOf(isSwitchChecked) }
    LaunchedEffect(isSwitchChecked) {
        isSwitchCheck.value = isSwitchChecked
    }
    val finalDate = remember { mutableLongStateOf(selectedDate) }
    LaunchedEffect(selectedDate) {
        finalDate.longValue = selectedDate
    }
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(8.dp)
            .height(72.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "Сделать до",
                modifier = Modifier.padding(vertical = 1.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = if (finalDate.longValue == 0L) "Дата не выбрана" else SimpleDateFormat(
                    "dd.MM.yyyy",
                    Locale.getDefault()
                ).format(finalDate.longValue),
                modifier = Modifier.padding(vertical = 1.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isSwitchCheck.value,
            onCheckedChange = { isChecked ->
                isSwitchCheck.value = isChecked

                if (isChecked) {
                    showDatePickerDialog(context) { date ->
                        finalDate.longValue = date
                        onDeadlineChanged(date)
                    }
                } else {
                    finalDate.longValue = 0L
                    onDeadlineChanged(null)
                }
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = MaterialTheme.colorScheme.secondary
            )
        )
    }

}

@Composable
fun ImportanceSelector(selectedImportance: Int, onImportanceChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .height(72.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            "Важность",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.weight(0.3f))
        Card(
            colors = CardDefaults.cardColors(
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Low importance
                Box(
                    Modifier
                        .width(48.dp)
                        .height(32.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (selectedImportance == 1) MaterialTheme.colorScheme.surface
                            else Color.Transparent
                        )
                        .clickable { onImportanceChange(1) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_low),
                        contentDescription = null
                    )
                }

                Text("|", color = Color.Gray, fontSize = 24.sp)

                // Medium importance
                Box(
                    Modifier
                        .width(48.dp)
                        .height(32.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .clickable { onImportanceChange(0) }
                        .background(
                            if (selectedImportance == 0) MaterialTheme.colorScheme.surface
                            else Color.Transparent
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("нет", color = Color.Gray)
                }

                Text("|", color = Color.Gray, fontSize = 24.sp)

                // High importance
                Box(
                    Modifier
                        .width(48.dp)
                        .height(32.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (selectedImportance == 2) MaterialTheme.colorScheme.surface
                            else Color.Transparent
                        )
                        .clickable { onImportanceChange(2) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_important),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopAppBar(onExit: () -> Unit, onSaveTask: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {},
        navigationIcon = {
            IconButton(onClick = onExit) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        actions = {
            Text(
                "СОХРАНИТЬ",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onSaveTask() }
            )
        }
    )
}

fun showDatePickerDialog(
    context: Context,
    onDateSelected: (Long) -> Unit
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->

            calendar.set(selectedYear, selectedMonth, selectedDay)

            onDateSelected(calendar.timeInMillis)
        },
        year,
        month,
        day
    )
    datePickerDialog.datePicker.minDate = System.currentTimeMillis()
    datePickerDialog.show()
}
