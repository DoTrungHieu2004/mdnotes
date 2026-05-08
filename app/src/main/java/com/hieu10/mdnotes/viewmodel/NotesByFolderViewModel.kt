package com.hieu10.mdnotes.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.db.repositories.FolderRepository
import com.hieu10.mdnotes.db.repositories.NoteRepository
import com.hieu10.mdnotes.ui.states.NoteWithMeta
import com.hieu10.mdnotes.ui.states.NotesByFolderUIState
import com.hieu10.mdnotes.ui.states.SortOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesByFolderViewModel(
    private val folderId: String,
    private val noteRepository: NoteRepository,
    private val folderRepository: FolderRepository
) : ViewModel() {

    private val _sortOrder = MutableStateFlow(SortOrder.LAST_MODIFIED)

    private val folderFlow = folderRepository.getFolderByIdFlow(folderId)
    private val notesFlow = noteRepository.getNotesByFolder(folderId)

    val state: StateFlow<NotesByFolderUIState> = combine(
        folderFlow,
        notesFlow,
        _sortOrder,
    ) { folder, notes, sortOrder ->
        val color = folder?.let { Color(android.graphics.Color.parseColor(it.colorHex)) }
        val sorted = when (sortOrder) {
            SortOrder.LAST_MODIFIED -> notes.sortedByDescending { it.updatedAt }
            SortOrder.CREATED_DATE -> notes.sortedByDescending { it.createdAt }
            SortOrder.TITLE_AZ -> notes.sortedBy { it.title.lowercase() }
            SortOrder.TITLE_ZA -> notes.sortedByDescending { it.title.lowercase() }
        }

        NotesByFolderUIState(
            folderName = folder?.name ?: R.string.unknown_folder,
            folderColor = color,
            pinnedNotes = sorted.filter { it.isPinned }.map {
                NoteWithMeta(note = it, folderColor = color, folderName = null)
            },
            allNotes = sorted.filter { !it.isPinned }.map { NoteWithMeta(note = it, folderColor = color, folderName = null) },
            isLoading = false,
            sortOrder = sortOrder
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = NotesByFolderUIState()
    )

    fun setSortOrder(order: SortOrder) { _sortOrder.value = order }

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
}