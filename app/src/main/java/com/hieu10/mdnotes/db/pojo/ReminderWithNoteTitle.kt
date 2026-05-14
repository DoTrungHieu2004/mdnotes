package com.hieu10.mdnotes.db.pojo

import androidx.room.Embedded
import com.hieu10.mdnotes.db.models.Reminder

data class ReminderWithNoteTitle(
    @Embedded
    val reminder: Reminder,
    val noteTitle: String?
)