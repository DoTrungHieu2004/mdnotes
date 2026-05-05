package com.hieu10.mdnotes.sample.data

import com.hieu10.mdnotes.db.models.Tag
import java.util.UUID

val singleTagSample = Tag(
    tagId = UUID.randomUUID().toString(),
    tagName = "Sample"
)

val tagSamples = listOf(
    Tag(tagName = "Urgent"),
    Tag(tagName = "Draft"),
    Tag(tagName = "Review-Required"),
    Tag(tagName = "Archive"),
    Tag(tagName = "Shared"),
    Tag(tagName = "Encrypted")
)