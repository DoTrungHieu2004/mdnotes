package com.hieu10.mdnotes.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = Note::class)
@Entity(tableName = "tb_notes_fts")
data class NoteFTS(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String
)