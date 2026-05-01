package com.hieu10.mdnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.mdnotes.db.repositories.FolderRepository
import com.hieu10.mdnotes.db.repositories.NoteRepository
import com.hieu10.mdnotes.ui.states.HomeState
import com.hieu10.mdnotes.ui.states.NoteWithMeta
import com.hieu10.mdnotes.ui.states.SortOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val noteRepository: NoteRepository,
    private val folderRepository: FolderRepository
) : ViewModel() {

    private val _sortOrder = MutableStateFlow(SortOrder.LAST_MODIFIED)

    val state: StateFlow<HomeState> = combine(
        noteRepository.getAllNotes(),
        _sortOrder
    ) { notes, sortOrder ->
        val sorted = when (sortOrder) {
            SortOrder.LAST_MODIFIED -> notes.sortedByDescending { it.updatedAt }
            SortOrder.CREATED_DATE -> notes.sortedByDescending { it.createdAt }
            SortOrder.TITLE_AZ -> notes.sortedBy { it.title.lowercase() }
            SortOrder.TITLE_ZA -> notes.sortedByDescending { it.title.lowercase() }
        }

        HomeState(
            pinnedNotes = sorted.filter { it.isPinned }
                .map { NoteWithMeta(it, folderColor = null, folderName = null) },
            allNotes = sorted.filter { !it.isPinned }
                .map { NoteWithMeta(it, folderColor = null, folderName = null) },
            isLoading = false,
            sortOrder = sortOrder
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState()
    )

    // ── ACTIONS ────────────────────────────────────

    fun archiveNote(noteId: String) {
        viewModelScope.launch {
            noteRepository.setArchived(noteId, true)
        }
    }

    fun trashNote(noteId: String) {
        viewModelScope.launch {
            noteRepository.trashNote(noteId)
        }
    }

    fun togglePin(noteId: String) {
        viewModelScope.launch {
            val note = noteRepository.getNoteById(noteId) ?: return@launch
            noteRepository.setPinned(noteId, !note.isPinned)
        }
    }

    fun toggleFavourite(noteId: String) {
        viewModelScope.launch {
            val note = noteRepository.getNoteById(noteId) ?: return@launch
            noteRepository.setFavourite(noteId, !note.isFavourite)
        }
    }

    fun setSortOrder(order: SortOrder) {
        _sortOrder.value = order
    }
}