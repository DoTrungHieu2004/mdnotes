package com.hieu10.mdnotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.mdnotes.MDNotesApp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.db.models.NoteRevision
import com.hieu10.mdnotes.db.repositories.NoteRepository
import com.hieu10.mdnotes.ui.states.RevisionHistoryUIState
import com.hieu10.mdnotes.utils.formatRevisionTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RevisionHistoryViewModel(
    application: Application,
    private val noteId: String,
    private val noteRepository: NoteRepository
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(RevisionHistoryUIState())
    val state: StateFlow<RevisionHistoryUIState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            noteRepository.getRevisionsForNote(noteId).collect { revisions ->
                _state.update { it.copy(revisions = revisions.sortedByDescending { r ->
                    r.revisionTimestamp
                }, isLoading = false) }
            }
        }
    }

    fun toggleExpand(revisionId: String) {
        _state.update {
            it.copy(
                expandedRevisionId = if (it.expandedRevisionId == revisionId) null else revisionId
            )
        }
    }

    fun restoreRevision(revision: NoteRevision) {
        viewModelScope.launch {
            _state.update { it.copy(isRestoring = true) }

            val currentNote = noteRepository.getNoteById(noteId)
            if (currentNote != null) {
                val desc = getApplication<MDNotesApp>().getString(
                    R.string.revision_description,
                    formatRevisionTimestamp(revision.revisionTimestamp)
                )
                noteRepository.saveRevision(currentNote, desc)
            }

            // Restore the content
            noteRepository.updateNoteContent(
                noteId = noteId,
                title = currentNote?.title ?: "",
                content = revision.contentSnapshot,
                saveRevision = false   // already saved above
            )
            _state.update { it.copy(isRestoring = false) }
        }
    }

    fun deleteRevision(revision: NoteRevision) {
        viewModelScope.launch {
            noteRepository.deleteRevisionById(revision.revisionId)
        }
    }
}