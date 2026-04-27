package com.hieu10.mdnotes.db.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "tb_notes",
    foreignKeys = [
        ForeignKey(
            entity = Folder::class,
            parentColumns = ["id"],
            childColumns = ["folderId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["folderId"]),
        Index(value = ["updatedAt"]),
        Index(value = ["title"])
    ]
)
data class Note(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val folderId: String? = null,
    val title: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isArchived: Boolean = false,
    val isPinned: Boolean = false,
    val isLocked: Boolean = false,
    val isFavourite: Boolean = false,
    val isTrashed: Boolean = false,
    val trashedAt: Long? = null,
    val syncStatus: Int = 0,        // 0 = synced, 1 = pending, etc.
    val wordCount: Int = 0
)