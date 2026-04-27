package com.hieu10.mdnotes.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tb_shortcuts")
data class Shortcut(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val targetId: String,   // ID of te referenced note, folder, or tag
    val type: ShortcutType,
    val position: Int
)