package com.hieu10.mdnotes.db.repositories

import com.hieu10.mdnotes.db.dao.NoteTagDAO
import com.hieu10.mdnotes.db.dao.TagDAO
import com.hieu10.mdnotes.db.models.Note
import com.hieu10.mdnotes.db.models.NoteTagCrossRef
import com.hieu10.mdnotes.db.models.Tag
import kotlinx.coroutines.flow.Flow

class TagRepository(
    private val tagDAO: TagDAO,
    private val noteTagDAO: NoteTagDAO
) {

    fun getAllTags(): Flow<List<Tag>> = tagDAO.getAllTagsFlow()

    suspend fun getTagById(tagId: String): Tag? = tagDAO.getTagById(tagId)

    suspend fun createTag(name: String): Result<Tag> {
        return try {
            val tag = Tag(tagName = name)
            tagDAO.insertTag(tag)
            Result.success(tag)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun renameTag(tagId: String, newName: String) {
        val tag = tagDAO.getTagById(tagId) ?: return
        tagDAO.updateTag(tag.copy(tagName = newName))
    }

    suspend fun deleteTag(tag: Tag) {
        tagDAO.deleteTag(tag)   // Cross-refs cascade-deleted by FK
    }

    // ── TAG-NOTE ASSOCIATIONS ────────────────────────

    fun getNotesForTag(tagId: String): Flow<List<Note>> = tagDAO.getNotesByTagId(tagId)

    suspend fun attachTag(noteId: String, tagId: String) {
        noteTagDAO.attachTagToNote(NoteTagCrossRef(noteId = noteId, tagId = tagId))
    }

    suspend fun detachTag(noteId: String, tagId: String) {
        noteTagDAO.detachTag(noteId, tagId)
    }

    fun getTagsForNote(noteId: String): Flow<List<NoteTagCrossRef>> = noteTagDAO.getTagsForNote(noteId)
}