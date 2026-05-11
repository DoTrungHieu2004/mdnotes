package com.hieu10.mdnotes.db.pojo

import androidx.room.Embedded
import com.hieu10.mdnotes.db.models.Note

data class SearchResult(
    @Embedded
    val note: Note,
    val snippet: String     // raw snippet from FTS, may contain <b> tags
)