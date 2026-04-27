package com.hieu10.mdnotes.db.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "tb_note_revisions",
    foreignKeys = [
        ForeignKey(
            entity = Note::class,
            parentColumns = ["id"],
            childColumns = ["noteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["noteId"])]
)
data class NoteRevision(
    @PrimaryKey val revisionId: String = UUID.randomUUID().toString(),
    val noteId: String,
    val contentSnapshot: String,
    val revisionTimestamp: Long = System.currentTimeMillis(),
    val revisionDescription: String? = null
)