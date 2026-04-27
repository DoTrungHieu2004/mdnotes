package com.hieu10.mdnotes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hieu10.mdnotes.db.models.NoteTagCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteTagDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun attachTagToNote(crossRef: NoteTagCrossRef)

    @Delete
    suspend fun removeTagFromNote(crossRef: NoteTagCrossRef)

    @Query("DELETE FROM tb_note_tag_cross_refs WHERE noteId = :noteId AND tagId = :tagId")
    suspend fun detachTag(noteId: String): Flow<List<NoteTagCrossRef>>

    @Query("SELECT * FROM tb_note_tag_cross_refs WHERE noteId = :noteId")
    fun getTagsForNote(noteId: String): Flow<List<NoteTagCrossRef>>

    @Query("SELECT * FROM tb_note_tag_cross_refs WHERE tagId = :tagId")
    fun getNotesForTag(tagId: String): Flow<List<NoteTagCrossRef>>

    @Query("DELETE FROM tb_note_tag_cross_refs WHERE noteId = :noteId")
    suspend fun deleteAllTagsForNote(noteId: String)
}