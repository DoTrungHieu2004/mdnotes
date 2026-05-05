package com.hieu10.mdnotes.db.models

import androidx.room.Embedded

data class TagWithNoteCount(
    @Embedded val tag: Tag,
    val noteCount: Int
)