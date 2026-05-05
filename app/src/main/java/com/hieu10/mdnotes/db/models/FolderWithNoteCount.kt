package com.hieu10.mdnotes.db.models

import androidx.room.Embedded

data class FolderWithNoteCount(
    @Embedded val folder: Folder,
    val noteCount: Int
)