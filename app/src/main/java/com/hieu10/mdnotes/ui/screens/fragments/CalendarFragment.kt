package com.hieu10.mdnotes.ui.screens.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.db.pojo.ReminderWithNoteTitle
import com.hieu10.mdnotes.sample.states.sampleCalendarEmptyState
import com.hieu10.mdnotes.sample.states.sampleCalendarLoadingState
import com.hieu10.mdnotes.sample.states.sampleCalendarState
import com.hieu10.mdnotes.ui.components.card.ReminderItem
import com.hieu10.mdnotes.ui.components.dialog.MonthYearPickerDialog
import com.hieu10.mdnotes.ui.components.grid.MonthCalendarGrid
import com.hieu10.mdnotes.ui.components.states.EmptyRemindersForDate
import com.hieu10.mdnotes.ui.states.CalendarUIState
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import com.hieu10.mdnotes.viewmodel.CalendarViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun CalendarFragment(
    viewModel: CalendarViewModel,
    onNoteClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showMonthPicker by remember { mutableStateOf(false) }

    CalendarContent(
        state = state,
        onPreviousMonth = viewModel::goToPreviousMonth,
        onNextMonth = viewModel::goToNextMonth,
        onDateSelected = viewModel::selectDate,
        onToggleComplete = viewModel::toggleComplete,
        onEditReminder = viewModel::editReminder,          // placeholder for now
        onDeleteReminder = viewModel::deleteReminder,
        onNoteClick = onNoteClick,
        onCreateReminder = viewModel::showCreateReminderDialog,   // placeholder
        onMonthYearClick = { showMonthPicker = true }
    )

    if (showMonthPicker) {
        MonthYearPickerDialog(
            initialYearMonth = YearMonth.of(state.currentYear, state.currentMonth),
            onDismiss = { showMonthPicker = false },
            onConfirm = { yearMonth ->
                viewModel.selectMonth(yearMonth)
                showMonthPicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarContent(
    state: CalendarUIState,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onToggleComplete: (ReminderWithNoteTitle) -> Unit,
    onEditReminder: (ReminderWithNoteTitle) -> Unit,
    onDeleteReminder: (ReminderWithNoteTitle) -> Unit,
    onNoteClick: (String) -> Unit,
    onCreateReminder: () -> Unit,
    onMonthYearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val monthYearLabel = remember(state.currentYear, state.currentMonth) {
        val date = LocalDate.of(state.currentYear, state.currentMonth, 1)
        date.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_bar_calendar)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                // Month header with navigation arrows
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onPreviousMonth) {
                        Icon(
                            imageVector = Icons.Filled.ChevronLeft,
                            contentDescription = stringResource(id = R.string.cd_previous_month)
                        )
                    }
                    TextButton(onClick = onMonthYearClick) {
                        Text(
                            text = monthYearLabel,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(onClick = onNextMonth) {
                        Icon(
                            imageVector = Icons.Filled.ChevronRight,
                            contentDescription = stringResource(id = R.string.cd_next_month)
                        )
                    }
                }
            }

            item {
                // Calendar grid
                MonthCalendarGrid(
                    year = state.currentYear,
                    month = state.currentMonth,
                    selectedDate = state.selectedDate,
                    reminderDates = state.reminderDatesForMonth,
                    onDateSelected = onDateSelected
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            // Reminders list for selected date
            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else if (state.remindersForSelectedDate.isEmpty()) {
                item {
                    EmptyRemindersForDate(
                        date = state.selectedDate,
                        onClickAdd = onCreateReminder,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                items(
                    items = state.remindersForSelectedDate,
                    key = { it.reminder.reminderId }
                ) { item ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ReminderItem(
                            reminder = item,
                            onToggleComplete = { onToggleComplete(item) },
                            onEdit = { onEditReminder(item) },
                            onDelete = { onDeleteReminder(item) },
                            onNoteClick = onNoteClick
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    MDNotesTheme(darkTheme = false) {
        CalendarContent(
            state = sampleCalendarState,
            onPreviousMonth = {},
            onNextMonth = {},
            onDateSelected = {},
            onToggleComplete = {},
            onEditReminder = {},
            onDeleteReminder = {},
            onNoteClick = {},
            onCreateReminder = {},
            onMonthYearClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    MDNotesTheme(darkTheme = true) {
        CalendarContent(
            state = sampleCalendarState,
            onPreviousMonth = {},
            onNextMonth = {},
            onDateSelected = {},
            onToggleComplete = {},
            onEditReminder = {},
            onDeleteReminder = {},
            onNoteClick = {},
            onCreateReminder = {},
            onMonthYearClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenEmptyLight() {
    MDNotesTheme(darkTheme = false) {
        CalendarContent(
            state = sampleCalendarEmptyState,
            onPreviousMonth = {},
            onNextMonth = {},
            onDateSelected = {},
            onToggleComplete = {},
            onEditReminder = {},
            onDeleteReminder = {},
            onNoteClick = {},
            onCreateReminder = {},
            onMonthYearClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenEmptyDark() {
    MDNotesTheme(darkTheme = true) {
        CalendarContent(
            state = sampleCalendarEmptyState,
            onPreviousMonth = {},
            onNextMonth = {},
            onDateSelected = {},
            onToggleComplete = {},
            onEditReminder = {},
            onDeleteReminder = {},
            onNoteClick = {},
            onCreateReminder = {},
            onMonthYearClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLoadingLight() {
    MDNotesTheme(darkTheme = false) {
        CalendarContent(
            state = sampleCalendarLoadingState,
            onPreviousMonth = {},
            onNextMonth = {},
            onDateSelected = {},
            onToggleComplete = {},
            onEditReminder = {},
            onDeleteReminder = {},
            onNoteClick = {},
            onCreateReminder = {},
            onMonthYearClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLoadingDark() {
    MDNotesTheme(darkTheme = true) {
        CalendarContent(
            state = sampleCalendarLoadingState,
            onPreviousMonth = {},
            onNextMonth = {},
            onDateSelected = {},
            onToggleComplete = {},
            onEditReminder = {},
            onDeleteReminder = {},
            onNoteClick = {},
            onCreateReminder = {},
            onMonthYearClick = {}
        )
    }
}