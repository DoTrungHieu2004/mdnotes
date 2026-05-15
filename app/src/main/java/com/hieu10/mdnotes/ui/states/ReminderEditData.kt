package com.hieu10.mdnotes.ui.states

data class ReminderEditData(
    val reminderId: String? = null,
    val taskDescription: String = "",
    val remindAt: Long = System.currentTimeMillis(),
    val noteId: String? = null
)