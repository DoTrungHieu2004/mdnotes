package com.hieu10.mdnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.mdnotes.db.repositories.FolderRepository
import com.hieu10.mdnotes.db.repositories.TagRepository
import com.hieu10.mdnotes.ui.states.FolderTagsUIState
import com.hieu10.mdnotes.ui.states.FoldersTagsTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FoldersTagsViewModel(
    private val folderRepository: FolderRepository,
    private val tagRepository: TagRepository
) : ViewModel() {

    private val _selectedTab = MutableStateFlow(FoldersTagsTab.FOLDERS)

    private val foldersFlow = folderRepository.getFoldersWithNoteCount()
    private val tagsFlow = tagRepository.getTagsWithNoteCount()

    val state: StateFlow<FolderTagsUIState> = combine(
        _selectedTab,
        foldersFlow,
        tagsFlow,
    ) { selectedTab, folders, tags ->
        FolderTagsUIState(
            selectedTab = selectedTab,
            folders = folders,
            tags = tags,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FolderTagsUIState()
    )

    fun onTabSelected(tab: FoldersTagsTab) {
        _selectedTab.value = tab
    }

    // ── Folder actions ──────────────────────────

    fun renameFolder(folderId: String) {
        // Not implemented yet.
        // TODO: A dialog will be shown here.
    }

    fun changeFolderColor(folderId: String) {
        // Not implemented yet.
        // TODO: A color dialog will be shown here.
    }

    fun deleteFolder(folderId: String) {
        viewModelScope.launch {}
    }

    // ── Tag actions ──────────────────────────

    fun renameTag(tagId: String) {
        // Dialog placeholder
        // TODO: A dialog will be shown here.
    }

    fun deleteTag(tagId: String) {
        viewModelScope.launch {
            // tagRepository.deleteTagById(tagId)
        }
    }

    // ── Create dialogs (placeholder) ─────────

    fun showCreateFolderDialog() {
        // trigger state for dialog – can be done in UI directly
    }

    fun showCreateTagDialog() {
        // trigger state for dialog
    }
}