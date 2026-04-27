package com.hieu10.mdnotes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hieu10.mdnotes.db.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    // ---------- BASIC CRUD ----------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    // ---------- SINGLE RETRIEVAL ----------
    @Query("SELECT * FROM tb_notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: String): Note?

    @Query("SELECT * FROM tb_notes WHERE id = :noteId")
    fun getNoteByIdFlow(noteId: String): Flow<Note?>

    // ---------- LIST QUERIES (observable) ----------
    @Query("""
        SELECT * FROM tb_notes
        WHERE isTrashed = 0 AND isArchived = 0
        ORDER BY isPinned DESC, updatedAt DESC
    """)
    fun getAllNotesFlow(): Flow<List<Note>>

    @Query("""
        SELECT * FROM tb_notes
        WHERE folderId = :folderId AND isTrashed = 0 AND isArchived = 0
        ORDER BY isPinned DESC, updatedAt DESC
    """)
    fun getNotesByFolderFlow(folderId: String): Flow<List<Note>>

    @Query("""
        SELECT * FROM tb_notes
        WHERE isPinned = 1 AND isTrashed = 0 AND isArchived = 0
        ORDER BY updatedAt DESC
    """)
    fun getPinnedNotesFlow(): Flow<List<Note>>

    @Query("""
        SELECT * FROM tb_notes
        WHERE isFavourite = 1 AND isTrashed = 0 AND isArchived = 0
        ORDER BY updatedAt DESC
    """)
    fun getFavouriteNotesFlow(): Flow<List<Note>>

    @Query("""
        SELECT * FROM tb_notes
        WHERE isArchived = 1 AND isTrashed = 0
        ORDER BY updatedAt DESC
    """)
    fun getArchivedNotesFlow(): Flow<List<Note>>

    @Query("SELECT * FROM tb_notes WHERE isTrashed = 1 ORDER BY trashedAt DESC")
    fun getTrashedNotesFlow(): Flow<List<Note>>

    // ---------- TRASH HANDLING ----------
    @Query("UPDATE tb_notes SET isTrashed = 1, trashedAt = :timestamp WHERE id = :noteId")
    suspend fun trashNote(noteId: String, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE tb_notes SET isTrashed = 0, trashedAt = NULL WHERE id = :noteId")
    suspend fun restoreNote(noteId: String)

    @Query("DELETE FROM tb_notes WHERE isTrashed = 1 AND trashedAt < :beforeTimestamp")
    suspend fun permanentlyDeleteNotesTrashedBefore(beforeTimestamp: Long)

    // ---------- UPDATES FOR SPECIFIC PROPERTIES ----------
    @Query("UPDATE tb_notes SET isPinned = :pinned WHERE id = :noteId")
    suspend fun setPinned(noteId: String, pinned: Boolean)

    @Query("UPDATE tb_notes SET isFavourite = :favourite WHERE id = :noteId")
    suspend fun setFavourite(noteId: String, favourite: Boolean)

    @Query("UPDATE tb_notes SET isArchived = :archived WHERE id = :noteId")
    suspend fun setArchived(noteId: String, archived: Boolean)

    @Query("UPDATE tb_notes SET isLocked = :locked WHERE id = :noteId")
    suspend fun setLocked(noteId: String, locked: Boolean)

    @Query("""
        UPDATE tb_notes
        SET title = :title, content = :content, updatedAt = :updatedAt, wordCount = :wordCount
        WHERE id = :noteId
    """)
    suspend fun updateNoteContent(
        noteId: String,
        title: String,
        content: String,
        updatedAt: Long = System.currentTimeMillis(),
        wordCount: Int
    )
}