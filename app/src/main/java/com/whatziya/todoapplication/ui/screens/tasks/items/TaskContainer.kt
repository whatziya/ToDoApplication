package com.whatziya.todoapplication.ui.screens.tasks.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.whatziya.todoapplication.R
import com.whatziya.todoapplication.data.database.entity.TaskEntity
import java.text.SimpleDateFormat
import java.util.Locale

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
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (taskItem.importance != 0) {
                Image(
                    modifier = Modifier.padding(end = 8.dp),
                    contentDescription = null,
                    painter = if (taskItem.importance == 1) {
                        painterResource(R.drawable.ic_low)
                    } else {
                        painterResource(
                            R.drawable.ic_important
                        )
                    }
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
