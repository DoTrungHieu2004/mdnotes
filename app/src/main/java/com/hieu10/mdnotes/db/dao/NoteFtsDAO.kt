package com.hieu10.mdnotes.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.hieu10.mdnotes.db.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteFtsDAO {

    /**
     * Search notes by title and content using FTS.
     * The MATCH syntax support prefix and phrase queries.
     *
     * For example: `query` = `"markdown"` or `query` = `"\"jetpack compose\""`
     */
    @Query("""
        SELECT tb_notes.* from tb_notes
        JOIN tb_notes_fts ON tb_notes.rowid = tb_notes_fts.rowid
        WHERE tb_notes_fts MATCH :query
        ORDER BY tb_notes.updatedAt DESC
    """)
    fun searchNotes(query: String): Flow<List<Note>>

    /**
     * Search and return only note IDs - useful for efficiency.
     */
    @Query("""
        SELECT tb_notes.id FROM tb_notes
        JOIN tb_notes_fts ON tb_notes.rowid = tb_notes_fts.rowid
        WHERE tb_notes_fts MATCH :query
    """)
    suspend fun searchNoteIds(query: String): List<String>

    /**
     * Rebuild the FTS index (e.g., after a migration or data import).
     */
    @Query("INSERT INTO tb_notes_fts(tb_notes_fts) VALUES('rebuild')")
    suspend fun rebuildFts()
}