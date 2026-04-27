package com.hieu10.mdnotes.db.converters

import androidx.room.TypeConverter
import com.hieu10.mdnotes.db.models.ShortcutType
import java.util.UUID

class Converters {
    @TypeConverter
    fun uuidToString(uuid: UUID?): String? = uuid?.toString()

    @TypeConverter
    fun stringToUuid(value: String?): UUID? = value?.let { UUID.fromString(it) }

    @TypeConverter
    fun shortcutTypeToString(type: ShortcutType?): String? = type?.name

    @TypeConverter
    fun stringToShortcutType(value: String?): ShortcutType? = value?.let { ShortcutType.valueOf(it) }
}