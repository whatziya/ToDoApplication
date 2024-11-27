package com.whatziya.todoapplication.ui.screens.task.items

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
                text = if (finalDate.longValue == 0L) {
                    "Дата не выбрана"
                } else {
                    SimpleDateFormat(
                        "dd.MM.yyyy",
                        Locale.getDefault()
                    ).format(finalDate.longValue)
                },
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
