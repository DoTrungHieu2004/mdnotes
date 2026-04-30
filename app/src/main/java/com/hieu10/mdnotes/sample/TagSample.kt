package com.hieu10.mdnotes.sample

import com.hieu10.mdnotes.db.models.Tag
import java.util.UUID

val sampleTagSet = listOf(
    Tag(tagId = UUID.randomUUID().toString(), tagName = "Sample"),
    Tag(tagId = UUID.randomUUID().toString(), tagName = "Note"),
    Tag(tagId = UUID.randomUUID().toString(), tagName = "Markdown"),
)