package com.hieu10.mdnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.mdnotes.db.pojo.ReminderWithNoteTitle
import com.hieu10.mdnotes.db.repositories.ReminderRepository
import com.hieu10.mdnotes.ui.states.CalendarUIState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarViewModel(
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> = _currentMonth.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    // Flow that emits all reminders for the currently displayed month (for dot indicators)
    private val monthReminders: Flow<List<ReminderWithNoteTitle>> = _currentMonth.flatMapLatest { yearMonth ->
        val start = yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val end = yearMonth.plusMonths(1).atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1
        reminderRepository.getRemindersBetweenWithNotes(start, end)
    }

    // Flow that emits reminders for the selected date (for the list)
    private val dayReminders: Flow<List<ReminderWithNoteTitle>> = _selectedDate.flatMapLatest { date ->
        val startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfDay = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1
        reminderRepository.getRemindersForDateWithNotes(startOfDay, endOfDay)
    }

    val state: StateFlow<CalendarUIState> = combine(
        _currentMonth,
        _selectedDate,
        monthReminders,
        dayReminders
    ) { month, date, monthList, dayList ->
        val reminderDates = monthList.map {
            Instant.ofEpochMilli(it.reminder.remindAt).atZone(ZoneId.systemDefault()).toLocalDate()
        }.toSet()

        CalendarUIState(
            currentYear = month.year,
            currentMonth = month.monthValue,
            selectedDate = date,
            remindersForSelectedDate = dayList,
            reminderDatesForMonth = reminderDates,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CalendarUIState()
    )

    fun goToPreviousMonth() {
        _currentMonth.update { it.minusMonths(1) }
    }

    fun goToNextMonth() {
        val next = _currentMonth.value.plusMonths(1)
        val now = YearMonth.now()
        if (!next.isAfter(now)) {
            _currentMonth.value = next
        }
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun selectMonth(yearMonth: YearMonth) {
        _currentMonth.value = yearMonth
        val lastDay = yearMonth.atEndOfMonth()
        val newDate = if (_selectedDate.value > lastDay) lastDay else _selectedDate.value
        _selectedDate.value = newDate
    }

    fun toggleComplete(item: ReminderWithNoteTitle) {
        viewModelScope.launch {
            reminderRepository.setCompleted(
                item.reminder.reminderId,
                !item.reminder.isCompleted
            )
        }
    }

    fun deleteReminder(item: ReminderWithNoteTitle) {
        viewModelScope.launch {
            reminderRepository.deleteReminderById(item.reminder.reminderId)
        }
    }

    // Placeholder for edit/create – will trigger UI later
    fun editReminder(item: ReminderWithNoteTitle) {
        // TODO: show edit dialog
    }

    fun showCreateReminderDialog() {
        // TODO: show create dialog
    }
}