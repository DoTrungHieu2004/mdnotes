package com.hieu10.mdnotes.db.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "tb_reminders",
    foreignKeys = [
        ForeignKey(
            entity = Note::class,
            parentColumns = ["id"],
            childColumns = ["noteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["remindAt"])]
)
data class Reminder(
    @PrimaryKey val reminderId: String = UUID.randomUUID().toString(),
    val noteId: String,
    val taskDescription: String,
    val remindAt: Long,
    val isCompleted: Boolean = false
)