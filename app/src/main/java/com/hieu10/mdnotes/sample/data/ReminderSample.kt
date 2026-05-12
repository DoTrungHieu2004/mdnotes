package com.hieu10.mdnotes.sample.data

import com.hieu10.mdnotes.db.models.Reminder
import com.hieu10.mdnotes.db.pojo.ReminderWithNoteTitle

val singleReminderSample = ReminderWithNoteTitle(
    reminder = Reminder(
        reminderId = "sample-001",
        noteId = "note-001",
        taskDescription = "Sample Reminder",
        remindAt = System.currentTimeMillis(),
        isCompleted = false
    ),
    noteTitle = "Sample Note"
)