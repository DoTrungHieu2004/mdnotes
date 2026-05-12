package com.hieu10.mdnotes.ui.components.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.theme.LocalMarkdownColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import com.hieu10.mdnotes.utils.formatLocalDate
import java.time.LocalDate

@Composable
fun EmptyRemindersForDate(
    date: LocalDate,
    onClickAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth().padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.CalendarMonth,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = LocalMarkdownColors.current.divider
            )
            Text(
                text = stringResource(id = R.string.reminder_empty_state_title, formatLocalDate(date)),
                style = MaterialTheme.typography.bodyLarge,
                color = LocalMarkdownColors.current.textSecondary
            )
            TextButton(onClick = onClickAdd) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = stringResource(id = R.string.btn_add_reminder))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStateLight() {
    MDNotesTheme(darkTheme = false) {
        EmptyRemindersForDate(
            date = LocalDate.now(),
            onClickAdd = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStateDark() {
    MDNotesTheme(darkTheme = true) {
        EmptyRemindersForDate(
            date = LocalDate.now(),
            onClickAdd = {}
        )
    }
}