package com.hieu10.mdnotes.ui.states

import com.hieu10.mdnotes.db.pojo.ReminderWithNoteTitle
import java.time.LocalDate

data class CalendarUIState(
    val currentYear: Int = LocalDate.now().year,
    val currentMonth: Int = LocalDate.now().monthValue,     // 1-12
    val selectedDate: LocalDate = LocalDate.now(),
    val remindersForSelectedDate: List<ReminderWithNoteTitle> = emptyList(),
    val reminderDatesForMonth: Set<LocalDate> = emptySet(),     // dates that have reminders
    val isLoading: Boolean = false
)