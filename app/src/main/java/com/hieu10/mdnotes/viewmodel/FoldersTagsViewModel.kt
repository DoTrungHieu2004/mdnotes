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
import kotlinx.coroutines.flow.asStateFlow
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

    fun createFolder(name: String, colorHex: String) {
        viewModelScope.launch {
            // Check duplicate
            if (folderRepository.getFolderByName(name) != null) {
                return@launch
            }
            folderRepository.createFolder(name, colorHex)
        }
        _showCreateFolderDialog.value = false
    }

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

    fun createTag(name: String) {
        viewModelScope.launch {
            if (tagRepository.getTagByName(name) != null) {
                // duplicate, ignore for now
                return@launch
            }
            tagRepository.createTag(name)
        }
        _showCreateTagDialog.value = false
    }

        fun renameTag(tagId: String) {
        // Dialog placeholder
        // TODO: A dialog will be shown here.
    }

    fun deleteTag(tagId: String) {
        viewModelScope.launch {
            // tagRepository.deleteTagById(tagId)
        }
    }

    // ── Dialog state ─────────────────────────

    private val _showCreateFolderDialog = MutableStateFlow(false)
    val showCreateFolderDialog: StateFlow<Boolean> = _showCreateFolderDialog.asStateFlow()

    private val _showCreateTagDialog = MutableStateFlow(false)
    val showCreateTagDialog: StateFlow<Boolean> = _showCreateTagDialog.asStateFlow()

    fun showCreateFolderDialog() {
        _showCreateFolderDialog.value = true
    }

    fun hideCreateFolderDialog() {
        _showCreateFolderDialog.value = false
    }

    fun showCreateTagDialog() {
        _showCreateTagDialog.value = true
    }

    fun hideCreateTagDialog() {
        _showCreateTagDialog.value = false
    }
}