package com.hieu10.mdnotes.db.repositories

import com.hieu10.mdnotes.db.dao.FolderDAO
import com.hieu10.mdnotes.db.models.Folder
import com.hieu10.mdnotes.db.models.FolderWithNoteCount
import kotlinx.coroutines.flow.Flow

class FolderRepository(private val folderDAO: FolderDAO) {

    fun getAllFolders(): Flow<List<Folder>> = folderDAO.getAllFoldersFlow()

    suspend fun getFolderById(id: String): Folder? = folderDAO.getFolderById(id)

    suspend fun createFolder(name: String, colorHex: String): Result<Folder> {
        return try {
            val folder = Folder(name = name, colorHex = colorHex)
            folderDAO.insertFolder(folder)
            Result.success(folder)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateFolder(folder: Folder) {
        folderDAO.updateFolder(folder)
    }

    suspend fun renameFolder(id: String, newName: String) {
        val folder = folderDAO.getFolderById(id) ?: return
        folderDAO.updateFolder(folder.copy(name = newName))
    }

    suspend fun changeFolderColor(id: String, colorHex: String) {
        val folder = folderDAO.getFolderById(id) ?: return
        folderDAO.updateFolder(folder.copy(colorHex = colorHex))
    }

    suspend fun deleteFolder(folder: Folder) {
        // Notes inside will have folderId set to NULL (ForeignKey.SET_NULL)
        folderDAO.deleteFolder(folder)
    }

    fun getFoldersWithNoteCount(): Flow<List<FolderWithNoteCount>> = folderDAO.getFoldersWithNoteCount()
}