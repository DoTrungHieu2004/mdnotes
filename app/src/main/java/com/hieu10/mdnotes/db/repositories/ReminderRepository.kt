package com.hieu10.mdnotes.db.repositories

import com.hieu10.mdnotes.db.dao.ReminderDAO
import com.hieu10.mdnotes.db.models.Reminder
import com.hieu10.mdnotes.db.pojo.ReminderWithNoteTitle
import kotlinx.coroutines.flow.Flow

class ReminderRepository(private val reminderDAO: ReminderDAO) {

    suspend fun updateReminder(reminder: Reminder) = reminderDAO.updateReminder(reminder)

    suspend fun getReminderById(reminderId: String): Reminder? =
        reminderDAO.getReminderById(reminderId)

    fun getRemindersBetweenWithNotes(start: Long, end: Long): Flow<List<ReminderWithNoteTitle>> =
        reminderDAO.getReminderBetweenWithNotes(start, end)

    fun getRemindersForDateWithNotes(startOfDay: Long, endOfDay: Long): Flow<List<ReminderWithNoteTitle>> =
        reminderDAO.getRemindersForDateWithNotes(startOfDay, endOfDay)

    suspend fun setCompleted(reminderId: String, completed: Boolean) =
        reminderDAO.setCompleted(reminderId, completed)

    suspend fun insertReminder(reminder: Reminder) =
        reminderDAO.insertReminder(reminder)

    suspend fun deleteReminderById(reminderId: String) =
        reminderDAO.deleteReminderById(reminderId)
}