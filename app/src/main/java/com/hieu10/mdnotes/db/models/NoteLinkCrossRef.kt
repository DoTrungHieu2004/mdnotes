package com.hieu10.mdnotes.db.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tb_note_link_cross_refs",
    foreignKeys = [
        ForeignKey(
            entity = Note::class,
            parentColumns = ["id"],
            childColumns = ["sourceNoteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Note::class,
            parentColumns = ["id"],
            childColumns = ["targetNoteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["targetNoteId"])]
)
data class NoteLinkCrossRef(
    @PrimaryKey(autoGenerate = true) val linkId: Long = 0,
    val sourceNoteId: String,
    val targetNoteId: String
)