package com.hieu10.mdnotes.sample.states

import com.hieu10.mdnotes.sample.data.singleNoteSample
import com.hieu10.mdnotes.ui.states.NoteEditorUIState

val noteEditorState = NoteEditorUIState(
    noteId = "new",
    title = "Sample note",
    content = singleNoteSample.content,
    isPreview = false
)