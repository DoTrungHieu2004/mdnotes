package com.hieu10.mdnotes.db.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "tb_tags",
    indices = [Index(value = ["tagName"], unique = true)]
)
data class Tag(
    @PrimaryKey val tagId: String = UUID.randomUUID().toString(),
    val tagName: String
)