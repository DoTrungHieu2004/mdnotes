package com.hieu10.mdnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.db.models.Note
import com.hieu10.mdnotes.db.repositories.FolderRepository
import com.hieu10.mdnotes.db.repositories.NoteRepository
import com.hieu10.mdnotes.db.repositories.TagRepository
import com.hieu10.mdnotes.ui.states.NoteEditorUIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteEditorViewModel(
    private val noteId: String,
    private val noteRepository: NoteRepository,
    private val folderRepository: FolderRepository,
    private val tagRepository: TagRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NoteEditorUIState(noteId = noteId))
    val state: StateFlow<NoteEditorUIState> = _state.asStateFlow()

    private var currentNote: Note? = null   // null until first save for new notes
    private var saveJob: Job? = null

    init {

    }

    // ── Load existing note ─────────────────────────

    private fun loadNote() {
        if (noteId == "new") return     // empty state already set

        viewModelScope.launch {
            val note = noteRepository.getNoteById(noteId)
            if (note == null) {
                // note not found - treat as new
                _state.value = NoteEditorUIState(noteId = "new")
                return@launch
            }

            currentNote = note

            val folderName = note.folderId?.let { folderRepository.getFolderById(it)?.name }
            val tags = tagRepository.getTagsForNoteDirect(noteId).first().map { it.tagName }

            _state.value = NoteEditorUIState(
                noteId = note.id,
                title = note.title,
                content = note.content,
                wordCount = note.wordCount,
                isPinned = note.isPinned,
                isFavourite = note.isFavourite,
                isLocked = note.isLocked,
                folderName = folderName,
                tags = tags,
                saveStatus = R.string.saved
            )
        }
    }

    // ── Title / Content changes ────────────────────

    fun onTitleChanged(newTitle: String) {
        _state.update { it.copy(title = newTitle, saveStatus = R.string.unsaved) }
        scheduleAutoSave()
    }

    fun onContentChanged(newContent: String) {
        val words = newContent.trim()
            .split("\\s+".toRegex())
            .filter { it.isNotEmpty() }
            .size
        _state.update {
            it.copy(
                content = newContent,
                wordCount = words,
                saveStatus = R.string.unsaved
            )
        }
        scheduleAutoSave()
    }

    private fun scheduleAutoSave() {
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            delay(1500L)    // debounce 1.5 seconds
            autoSave()
        }
    }

    // ── Auto‑save (create or update) ──────────────

    fun autoSave() {
        viewModelScope.launch {
            val s = _state.value
            if (s.title.isBlank() && s.content.isBlank()) return@launch

            if (currentNote == null) {
                // Create a new note
                val created = noteRepository.createNote(
                    title = s.title.ifBlank { "Untitled" },
                    content = s.content,
                    folderId = null     // folder can be set later
                )
                currentNote = created
                _state.update {
                    it.copy(
                        noteId = created.id,
                        saveStatus = R.string.saved
                    )
                }
            } else {
                // Update existing
                noteRepository.updateNoteContent(
                    noteId = currentNote!!.id,
                    title = s.title,
                    content = s.content,
                    saveRevision = true
                )
                _state.update { it.copy(saveStatus = R.string.unsaved) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveJob?.cancel()
        // Fire a final save synchronously is not possible; autoSave() last scheduled one may still run.
    }

    // ── Toggle properties ──────────────────────────

    fun togglePin() {
        val new = !_state.value.isPinned
        _state.update { it.copy(isPinned = new) }
        currentNote?.id?.let {
            viewModelScope.launch { noteRepository.setPinned(it, new) }
        }
    }

    fun toggleFavourite() {
        val new = !_state.value.isFavourite
        _state.update { it.copy(isFavourite = new) }
        currentNote?.id?.let {
            viewModelScope.launch { noteRepository.setFavourite(it, new) }
        }
    }

    fun toggleLocked() {
        val new = !_state.value.isLocked
        _state.update { it.copy(isLocked = new) }
        currentNote?.id?.let {
            viewModelScope.launch { noteRepository.setLocked(it, new) }
        }
    }

    fun togglePreview() {
        _state.update { it.copy(isPreview = !it.isPreview) }
    }

    // ── Formatting helpers (basic, without selection) ─

    private fun insertFormatting(prefix: String, suffix: String) {
        val current = _state.value.content
        val newContent = current + "\n" + prefix + "text" + suffix
        onContentChanged(newContent)
    }

    fun insertBold() = insertFormatting("**", "**")
    fun insertItalic() = insertFormatting("*", "*")
    fun insertHeading() = insertFormatting("## ", "")
    fun insertStrikethrough() = insertFormatting("~~", "~~")
    fun insertBulletList() = insertFormatting("- ", "")
    fun insertLink() = insertFormatting("[", "](url)")
    fun insertImage() = insertFormatting("![alt]", "(uri)")
    fun insertCodeBlock() = insertFormatting("```\n", "\n```")
    fun insertAttachment() { /* will open file picker later */ }

    // ── Overflow actions ────────────────────────────

    fun onOverflowAction(action: String) {
        when (action) {
            "revisions" -> { /* navigate to revisions screen */ }
            "info"      -> { /* show note info dialog */ }
            "archive"   -> viewModelScope.launch {
                currentNote?.id?.let { noteRepository.setArchived(it, true) }
            }
            "trash"     -> viewModelScope.launch {
                currentNote?.id?.let { noteRepository.trashNote(it) }
            }
        }
    }
}