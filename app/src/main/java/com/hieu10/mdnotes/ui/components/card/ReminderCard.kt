package com.hieu10.mdnotes.ui.components.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.db.pojo.ReminderWithNoteTitle
import com.hieu10.mdnotes.sample.data.singleReminderSample
import com.hieu10.mdnotes.ui.theme.LocalMarkdownColors
import com.hieu10.mdnotes.ui.theme.LocalSemanticColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import com.hieu10.mdnotes.utils.formatTime

@Composable
fun ReminderItem(
    reminder: ReminderWithNoteTitle,
    onToggleComplete: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onNoteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }

    val isOverdue = reminder.reminder.remindAt < System.currentTimeMillis() && !reminder.reminder.isCompleted
    val timeColor = when {
        reminder.reminder.isCompleted -> LocalSemanticColors.current.hint
        isOverdue -> LocalSemanticColors.current.warning
        else -> LocalSemanticColors.current.info
    }
    val taskColor = when {
        reminder.reminder.isCompleted -> LocalMarkdownColors.current.textSecondary
        else -> LocalMarkdownColors.current.textPrimary
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Checkbox(
                checked = reminder.reminder.isCompleted,
                onCheckedChange = { onToggleComplete() },
                colors = CheckboxDefaults.colors(
                    checkedColor = LocalSemanticColors.current.success,
                    uncheckedColor = LocalMarkdownColors.current.textSecondary
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = formatTime(reminder.reminder.remindAt),
                        style = MaterialTheme.typography.labelLarge,
                        color = timeColor
                    )
                    if (isOverdue) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Filled.Warning,
                            contentDescription = stringResource(id = R.string.cd_overdue),
                            tint = timeColor,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = reminder.reminder.taskDescription,
                    style = MaterialTheme.typography.bodySmall.copy(
                        textDecoration = if (reminder.reminder.isCompleted)
                            TextDecoration.LineThrough
                        else
                            TextDecoration.None
                    ),
                    color = taskColor
                )
                if (reminder.noteTitle != null && reminder.reminder.noteId != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    TextButton(
                        onClick = { onNoteClick(reminder.reminder.noteId) },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Note,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = reminder.noteTitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = stringResource(id = R.string.cd_more)
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.dropdown_edit)) },
                        onClick = { showMenu = false; onEdit() }
                    )
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.dropdown_delete)) },
                        onClick = { showMenu = false; onDelete() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardLight() {
    MDNotesTheme(darkTheme = false) {
        ReminderItem(
            reminder = singleReminderSample,
            onToggleComplete = {},
            onEdit = {},
            onDelete = {},
            onNoteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardDark() {
    MDNotesTheme(darkTheme = true) {
        ReminderItem(
            reminder = singleReminderSample,
            onToggleComplete = {},
            onEdit = {},
            onDelete = {},
            onNoteClick = {}
        )
    }
}