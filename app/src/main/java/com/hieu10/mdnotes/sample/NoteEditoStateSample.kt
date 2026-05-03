package com.hieu10.mdnotes.sample

import com.hieu10.mdnotes.ui.states.NoteEditorUIState

val noteEditorState = NoteEditorUIState(
    noteId = "new",
    title = "Sample note",
    content = singleNoteSample.content,
    isPreview = false
)