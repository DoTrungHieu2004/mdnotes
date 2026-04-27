package com.hieu10.mdnotes.db.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "tb_assets",
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
data class Asset(
    @PrimaryKey val assetId: String = UUID.randomUUID().toString(),
    val noteId: String,
    val fileName: String,
    val localUri: String,
    val mimeType: String,
    val fileSize: Long
)