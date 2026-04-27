package com.hieu10.mdnotes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hieu10.mdnotes.db.models.Folder
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
}