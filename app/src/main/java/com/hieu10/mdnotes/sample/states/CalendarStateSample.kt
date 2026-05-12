package com.hieu10.mdnotes.sample.states

import com.hieu10.mdnotes.db.models.Reminder
import com.hieu10.mdnotes.db.pojo.ReminderWithNoteTitle
import com.hieu10.mdnotes.ui.states.CalendarUIState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

val sampleCalendarState = CalendarUIState(
    currentYear = 2026,
    currentMonth = 4,
    selectedDate = LocalDate.of(2026, 4, 27),
    remindersForSelectedDate = listOf(
        ReminderWithNoteTitle(
            reminder = Reminder(
                reminderId = "rem-001",
                noteId = "sample-001",
                taskDescription = "Finish quarterly report draft",
                remindAt = LocalDateTime.of(2026, 4, 27, 9, 0)
                    .toInstant(ZoneOffset.UTC)
                    .toEpochMilli(),
                isCompleted = true
            ),
            noteTitle = "Weekly Meeting Notes"
        ),
        ReminderWithNoteTitle(
            reminder = Reminder(
                reminderId = "rem-002",
                noteId = "",
                taskDescription = "Call dentist for appointment",
                remindAt = LocalDateTime.of(2026, 4, 27, 14, 0)
                    .toInstant(ZoneOffset.UTC)
                    .toEpochMilli(),
                isCompleted = false
            ),
            noteTitle = null
        ),
        ReminderWithNoteTitle(
            reminder = Reminder(
                reminderId = "rem-003",
                noteId = "sample-004",
                taskDescription = "Review project ideas and finalize scope",
                remindAt = LocalDateTime.of(2026, 4, 27, 0, 0)
                    .toInstant(ZoneOffset.UTC)
                    .toEpochMilli(),
                isCompleted = false
            ),
            noteTitle = "Project Ideas"
        ),
        ReminderWithNoteTitle(
            reminder = Reminder(
                reminderId = "rem-004",
                noteId = "",
                taskDescription = "Buy groceries for dinner party",
                remindAt = LocalDateTime.of(2026, 4, 27, 16, 30)
                    .toInstant(ZoneOffset.UTC)
                    .toEpochMilli(),
                isCompleted = false
            ),
            noteTitle = null
        )
    ),
    reminderDatesForMonth = setOf(
        LocalDate.of(2026, 4, 3),
        LocalDate.of(2026, 4, 7),
        LocalDate.of(2026, 4, 12),
        LocalDate.of(2026, 4, 15),
        LocalDate.of(2026, 4, 18),
        LocalDate.of(2026, 4, 22),
        LocalDate.of(2026, 4, 25),
        LocalDate.of(2026, 4, 27),
        LocalDate.of(2026, 4, 29)
    ),
    isLoading = false
)

val sampleCalendarLoadingState = CalendarUIState(
    currentYear = 2026,
    currentMonth = 4,
    selectedDate = LocalDate.of(2026, 4, 27),
    remindersForSelectedDate = emptyList(),
    reminderDatesForMonth = emptySet(),
    isLoading = true
)

val sampleCalendarEmptyState = CalendarUIState(
    currentYear = 2026,
    currentMonth = 5,
    selectedDate = LocalDate.of(2026, 5, 10),
    remindersForSelectedDate = emptyList(),
    reminderDatesForMonth = setOf(
        LocalDate.of(2026, 5, 1),
        LocalDate.of(2026, 5, 15),
        LocalDate.of(2026, 5, 31)
    ),
    isLoading = false
)