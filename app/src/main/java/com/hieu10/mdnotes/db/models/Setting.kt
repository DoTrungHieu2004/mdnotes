package com.hieu10.mdnotes.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_settings")
data class Setting(
    @PrimaryKey val key: String,
    val value: String
)