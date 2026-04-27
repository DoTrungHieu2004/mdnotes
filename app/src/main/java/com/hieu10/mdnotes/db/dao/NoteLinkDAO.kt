package com.hieu10.mdnotes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hieu10.mdnotes.db.models.NoteLinkCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteLinkDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLink(link: NoteLinkCrossRef)

    @Delete
    suspend fun deleteLink(link: NoteLinkCrossRef)

    @Query("DELETE FROM tb_note_link_cross_refs WHERE linkId = :linkId")
    suspend fun deleteLinkById(linkId: Long)

    @Query("""
        DELETE FROM tb_note_link_cross_refs
        WHERE sourceNoteId = :sourceNoteId AND targetNoteId = :targetNoteId
    """)
    suspend fun deleteLinkBetween(sourceNoteId: String, targetNoteId: String)

    /**
     * All outgoing links from a note (notes that this note links to).
     */
    @Query("SELECT * FROM tb_note_link_cross_refs WHERE sourceNoteId = :noteId")
    fun getOutgoingLinksFlow(noteId: String): Flow<List<NoteLinkCrossRef>>

    /**
     * All incoming links to a note (backlinks - notes that link to this note).
     */
    @Query("SELECT * FROM tb_note_link_cross_refs WHERE targetNoteId = :noteId")
    fun getIncomingLinksFlow(noteId: String): Flow<List<NoteLinkCrossRef>>

    /**
     * All links (both directions) involving a note.
     */
    @Query("""
        SELECT * FROM tb_note_link_cross_refs
        WHERE sourceNoteId = :noteId OR targetNoteId = :noteId
    """)
    fun getAllLinksForNoteFlow(noteId: String): Flow<List<NoteLinkCrossRef>>

    @Query("DELETE FROM tb_note_link_cross_refs WHERE sourceNoteId = :noteId")
    suspend fun deleteAllOutgoingLinksForNote(noteId: String)

    @Query("DELETE FROM tb_note_link_cross_refs WHERE targetNoteId = :noteId")
    suspend fun deleteAllIncomingLinksForNote(noteId: String)
}