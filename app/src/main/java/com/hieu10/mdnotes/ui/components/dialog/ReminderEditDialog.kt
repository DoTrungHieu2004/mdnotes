package com.hieu10.mdnotes.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.states.ReminderEditData
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import com.hieu10.mdnotes.utils.formatDate
import com.hieu10.mdnotes.utils.formatTimeEditDialog
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderEditDialog(
    initial: ReminderEditData,
    onDismiss: () -> Unit,
    onConfirm: (ReminderEditData) -> Unit
) {
    var taskDescription by remember { mutableStateOf(initial.taskDescription) }
    var remindAt by remember { mutableStateOf(initial.remindAt) }

    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = remindAt
    )
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initial.reminderId == null) {
                    stringResource(id = R.string.dialog_new_reminder)
                } else {
                    stringResource(id = R.string.dialog_edit_reminder)
                }
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = taskDescription,
                    onValueChange = { taskDescription = it },
                    label = { Text(text = stringResource(id = R.string.hint_task_description)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Date & time selection
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = formatDate(remindAt),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    TextButton(
                        onClick = { showTimePicker = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = formatTimeEditDialog(remindAt),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        ReminderEditData(
                            reminderId = initial.reminderId,
                            taskDescription = taskDescription,
                            remindAt = remindAt,
                            noteId = initial.noteId
                        )
                    )
                }
            ) {
                Text(text = stringResource(id = R.string.btn_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.btn_cancel))
            }
        }
    )

    // Date picker dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    dateState.selectedDateMillis?.let { millis ->
                        // Keep same time of day from current remindAt
                        val cal = Calendar.getInstance()
                        cal.timeInMillis = remindAt
                        val hour = cal.get(Calendar.HOUR_OF_DAY)
                        val minute = cal.get(Calendar.MINUTE)

                        cal.timeInMillis = millis
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        remindAt = cal.timeInMillis
                    }
                    showDatePicker = false
                }) {
                    Text(text = stringResource(id = R.string.btn_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(text = stringResource(id = R.string.btn_cancel))
                }
            }
        ) {
            DatePicker(state = dateState)
        }
    }

    // Time picker dialog
    if (showTimePicker) {
        val cal = Calendar.getInstance().apply { timeInMillis = remindAt }
        val initialHour = cal.get(Calendar.HOUR_OF_DAY)
        val initialMinute = cal.get(Calendar.MINUTE)

        val timeState = rememberTimePickerState(
            initialHour = initialHour,
            initialMinute = initialMinute,
            is24Hour = true
        )

        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            title = { Text(text = stringResource(id = R.string.dialog_select_time)) },
            text = { TimePicker(state = timeState) },
            confirmButton = {
                TextButton(onClick = {
                    cal.set(Calendar.HOUR_OF_DAY, timeState.hour)
                    cal.set(Calendar.MINUTE, timeState.minute)
                    remindAt = cal.timeInMillis
                    showTimePicker = false
                }) {
                    Text(text = stringResource(id = R.string.btn_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text(text = stringResource(id = R.string.btn_cancel))
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDialogLight() {
    MDNotesTheme(darkTheme = false) {
        ReminderEditDialog(
            initial = ReminderEditData(),
            onDismiss = {},
            onConfirm = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDialogDark() {
    MDNotesTheme(darkTheme = true) {
        ReminderEditDialog(
            initial = ReminderEditData(),
            onDismiss = {},
            onConfirm = {}
        )
    }
}