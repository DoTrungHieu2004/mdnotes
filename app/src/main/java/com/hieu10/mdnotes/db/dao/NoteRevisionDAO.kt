package com.hieu10.mdnotes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hieu10.mdnotes.db.models.NoteRevision
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteRevisionDAO {

    @Insert
    suspend fun insertRevision(revision: NoteRevision)

    @Delete
    suspend fun deleteRevision(revision: NoteRevision)

    @Query("SELECT * FROM tb_note_revisions WHERE revisionId = :revisionId")
    suspend fun getRevisionById(revisionId: String): NoteRevision?

    @Query("""
        SELECT * FROM tb_note_revisions
        WHERE noteId = :noteId
        ORDER BY revisionTimestamp DESC
    """)
    fun getRevisionsForNoteFlow(noteId: String): Flow<List<NoteRevision>>

    @Query("DELETE FROM tb_note_revisions WHERE noteId = :noteId")
    suspend fun deleteAllRevisionsForNote(noteId: String)

    @Query("DELETE FROM tb_note_revisions WHERE revisionId = :revisionId")
    suspend fun deleteRevisionById(revisionId: String)
}