package com.hieu10.mdnotes.ui.components.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.theme.LocalSemanticColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import java.time.LocalDate
import kotlin.math.ceil

@Composable
fun MonthCalendarGrid(
    year: Int,
    month: Int,                             // 1‑based
    selectedDate: LocalDate?,
    reminderDates: Set<LocalDate>,          // dates that have at least one reminder
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val today = remember { LocalDate.now() }
    val firstDayOfMonth = remember(year, month) { LocalDate.of(year, month, 1) }
    val daysInMonth = firstDayOfMonth.lengthOfMonth()
    val startDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7    // Monday=0 … Sunday=6

    val daysOfWeekLabels = stringArrayResource(id = R.array.days)

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        // Day headers
        Row(modifier = Modifier.fillMaxWidth()) {
            daysOfWeekLabels.forEach { label ->
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = LocalSemanticColors.current.hint,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Day grid
        var day = 1
        val rows = ceil((startDayOfWeek + daysInMonth) / 7.0).toInt()
        for (row in 0 until rows) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0..6) {
                    if ((row == 0 && col < startDayOfWeek) || day > daysInMonth) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f))
                    } else {
                        val currentDate = firstDayOfMonth.withDayOfMonth(day)
                        val isSelected = selectedDate == currentDate
                        val isToday = currentDate == today
                        val hasReminder = currentDate in reminderDates

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clickable { onDateSelected(currentDate) },
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .then(
                                        if (isSelected) Modifier.border(
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        ) else Modifier
                                    )
                                    .background(
                                        color = if (isToday)
                                            MaterialTheme.colorScheme.primaryContainer
                                        else
                                            Color.Transparent,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = day.toString(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = when {
                                        isToday -> MaterialTheme.colorScheme.onPrimaryContainer
                                        else -> MaterialTheme.colorScheme.onSurface
                                    }
                                )
                            }
                            if (hasReminder && !isToday) {
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        )
                                        .align(Alignment.BottomCenter)
                                        .offset(y = (-2).dp)
                                )
                            }
                        }
                        day++
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCalendarGridLight() {
    MDNotesTheme(darkTheme = false) {
        MonthCalendarGrid(
            year = 2026,
            month = 1,
            selectedDate = null,
            reminderDates = emptySet(),
            onDateSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCalendarGridDark() {
    MDNotesTheme(darkTheme = true) {
        MonthCalendarGrid(
            year = 2026,
            month = 1,
            selectedDate = null,
            reminderDates = emptySet(),
            onDateSelected = {}
        )
    }
}