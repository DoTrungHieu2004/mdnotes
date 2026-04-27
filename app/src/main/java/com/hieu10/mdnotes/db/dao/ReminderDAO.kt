package com.hieu10.mdnotes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hieu10.mdnotes.db.models.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("SELECT * FROM tb_reminders WHERE reminderId = :reminderId")
    suspend fun getReminderById(reminderId: String): Reminder?

    @Query("SELECT * FROM tb_reminders WHERE noteId = :noteId ORDER BY remindAt ASC")
    fun getRemindersForNoteFlow(noteId: String): Flow<List<Reminder>>

    @Query("""
        SELECT * FROM tb_reminders
        WHERE remindAt >= :fromTime AND remindAt <= :toTime
        ORDER BY remindAt ASC
    """)
    fun getRemindersInTimeRangeFlow(fromTime: Long, toTime: Long): Flow<List<Reminder>>

    @Query("SELECT * FROM tb_reminders ORDER BY remindAt ASC")
    fun getAllRemindersFlow(): Flow<List<Reminder>>

    @Query("UPDATE tb_reminders SET isCompleted = :completed WHERE reminderId = :reminderId")
    suspend fun setCompleted(reminderId: String, completed: Boolean)

    @Query("DELETE FROM tb_reminders WHERE reminderId = :reminderId")
    suspend fun deleteReminderById(reminderId: String)

    @Query("DELETE FROM tb_reminders WHERE noteId = :noteId")
    suspend fun deleteAllRemindersForNote(noteId: String)
}