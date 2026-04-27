package com.hieu10.mdnotes.db.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "tb_folders",
    indices = [Index(value = ["name"], unique = true)]
)
data class Folder(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val colorHex: String,
    val createdAt: Long = System.currentTimeMillis()
)