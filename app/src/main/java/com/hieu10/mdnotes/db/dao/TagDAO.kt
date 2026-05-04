package com.hieu10.mdnotes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hieu10.mdnotes.db.models.Note
import com.hieu10.mdnotes.db.models.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTag(tag: Tag)

    @Update
    suspend fun updateTag(tag: Tag)

    @Delete
    suspend fun deleteTag(tag: Tag)

    @Query("SELECT * FROM tb_tags ORDER BY tagName ASC")
    fun getAllTagsFlow(): Flow<List<Tag>>

    @Query("SELECT * FROM tb_tags WHERE tagId = :tagId")
    suspend fun getTagById(tagId: String): Tag?

    @Query("SELECT * FROM tb_tags WHERE tagName = :name LIMIT 1")
    suspend fun getTagByName(name: String): Tag?

    /**
     * Returns all notes tagged with a specific tag.
     */
    @Query("""
        SELECT tb_notes.* FROM tb_notes
        INNER JOIN tb_note_tag_cross_refs ON tb_notes.id = tb_note_tag_cross_refs.noteId
        WHERE tb_note_tag_cross_refs.tagId = :tagId
        ORDER BY tb_notes.updatedAt DESC
    """)
    fun getNotesByTagId(tagId: String): Flow<List<Note>>

    /**
     * Get all tags attached to a note.
     */
    @Query("""
        SELECT tb_tags.* FROM tb_tags
        INNER JOIN tb_note_tag_cross_refs ON tb_tags.tagId = tb_note_tag_cross_refs.tagId
        WHERE tb_note_tag_cross_refs.noteId = :noteId
    """)
    fun getTagsForNoteDirect(noteId: String): Flow<List<Tag>>
}