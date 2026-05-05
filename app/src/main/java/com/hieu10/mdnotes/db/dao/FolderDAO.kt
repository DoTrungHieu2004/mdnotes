package com.hieu10.mdnotes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hieu10.mdnotes.db.models.Folder
import com.hieu10.mdnotes.db.models.FolderWithNoteCount
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFolder(folder: Folder)

    @Update
    suspend fun updateFolder(folder: Folder)

    @Delete
    suspend fun deleteFolder(folder: Folder)

    @Query("SELECT * FROM tb_folders ORDER BY createdAt DESC")
    fun getAllFoldersFlow(): Flow<List<Folder>>

    @Query("SELECT * FROM tb_folders WHERE id = :folderId")
    suspend fun getFolderById(folderId: String): Folder?

    @Query("SELECT * FROM tb_folders WHERE name = :name LIMIT 1")
    suspend fun getFolderByName(name: String): Folder?

    @Query("""
        SELECT tb_folders.*, COUNT(tb_notes.id) AS noteCount
        FROM tb_folders
        LEFT JOIN tb_notes ON tb_notes.folderId = tb_folders.id
            AND tb_notes.isTrashed = 0
            AND tb_notes.isArchived = 0
        GROUP BY tb_folders.id
        ORDER BY tb_folders.name ASC
    """)
    fun getFoldersWithNoteCount(): Flow<List<FolderWithNoteCount>>
}