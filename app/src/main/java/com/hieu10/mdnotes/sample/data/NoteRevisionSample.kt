package com.hieu10.mdnotes.sample.data

import com.hieu10.mdnotes.db.models.NoteRevision
import java.util.UUID

val singleNoteRevisionSample = NoteRevision(
    revisionId = UUID.randomUUID().toString(),
    noteId = "note-id",
    contentSnapshot = singleNoteSample.content,
    revisionTimestamp = System.currentTimeMillis(),
    revisionDescription = "revision-description"
)

val revisionHistorySample = listOf(
    NoteRevision(
        revisionId = UUID.randomUUID().toString(),
        noteId = "note-id",
        contentSnapshot = "# Initial Draft\nThis was the first version of the note.",
        revisionTimestamp = System.currentTimeMillis() - 86400000, // 1 day ago
        revisionDescription = "Initial creation"
    ),
    NoteRevision(
        revisionId = UUID.randomUUID().toString(),
        noteId = "note-id",
        contentSnapshot = "# Initial Draft\nAdded some more details to the content.",
        revisionTimestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
        revisionDescription = "Added details"
    ),
    NoteRevision(
        revisionId = UUID.randomUUID().toString(),
        noteId = "note-id",
        contentSnapshot = singleNoteSample.content,
        revisionTimestamp = System.currentTimeMillis(),
        revisionDescription = "Latest update"
    )
)