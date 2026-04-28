package com.hieu10.mdnotes.db.repositories

import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.db.dao.NoteDAO
import com.hieu10.mdnotes.db.dao.NoteFtsDAO
import com.hieu10.mdnotes.db.dao.NoteLinkDAO
import com.hieu10.mdnotes.db.dao.NoteRevisionDAO
import com.hieu10.mdnotes.db.models.Note
import com.hieu10.mdnotes.db.models.NoteLinkCrossRef
import com.hieu10.mdnotes.db.models.NoteRevision
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val noteDAO: NoteDAO,
    private val noteFtsDAO: NoteFtsDAO,
    private val noteRevisionDAO: NoteRevisionDAO,
    private val noteLinkDAO: NoteLinkDAO
) {

    // ── BASIC CRUD ────────────────────────────────────

    suspend fun createNote(
        title: String,
        content: String = "",
        folderId: String? = null
    ): Note {
        val note = Note(
            title = title,
            content = content,
            folderId = folderId,
            wordCount = countWords(content)
        )
        noteDAO.insertNote(note)
        saveRevision(note, R.string.initial_note_version.toString())
        return note
    }

    suspend fun updateNoteContent(
        noteId: String,
        title: String,
        content: String,
        saveRevision: Boolean = true
    ) {
        val oldNote = noteDAO.getNoteById(noteId) ?: return
        noteDAO.updateNoteContent(
            noteId = noteId,
            title = title,
            content = content,
            wordCount = countWords(content)
        )
        if (saveRevision) {
            saveRevision(oldNote, R.string.auto_save_note.toString())
        }
    }

    suspend fun getNoteById(noteId: String): Note? = noteDAO.getNoteById(noteId)

    fun getNoteByIdFlow(noteId: String): Flow<Note?> = noteDAO.getNoteByIdFlow(noteId)

    fun getAllNotes(): Flow<List<Note>> = noteDAO.getAllNotesFlow()

    fun getNotesByFolder(folderId: String): Flow<List<Note>> = noteDAO.getNotesByFolderFlow(folderId)

    fun getPinnedNotes(): Flow<List<Note>> = noteDAO.getPinnedNotesFlow()

    fun getFavouriteNotes(): Flow<List<Note>> = noteDAO.getFavouriteNotesFlow()

    fun getArchivedNotes(): Flow<List<Note>> = noteDAO.getArchivedNotesFlow()

    fun getTrashedNotes(): Flow<List<Note>> = noteDAO.getTrashedNotesFlow()

    // ── NOTE STATE PROPERTIES ────────────────────────────────────

    suspend fun setPinned(noteId: String, pinned: Boolean) = noteDAO.setPinned(noteId, pinned)
    suspend fun setFavourite(noteId: String, favourite: Boolean) = noteDAO.setFavourite(noteId, favourite)
    suspend fun setArchived(noteId: String, archived: Boolean) = noteDAO.setArchived(noteId, archived)
    suspend fun setLocked(noteId: String, locked: Boolean) = noteDAO.setLocked(noteId, locked)

    // ── TRASH / RESTORE ────────────────────────────────────

    suspend fun trashNote(noteId: String) = noteDAO.trashNote(noteId)
    suspend fun restoreNote(noteId: String) = noteDAO.restoreNote(noteId)
    suspend fun permanentlyDeleteNotesTrashedBefore(timestamp: Long) =
        noteDAO.permanentlyDeleteNotesTrashedBefore(timestamp)

    // ── FTS SEARCH ────────────────────────────────────

    /**
     * Live search with a raw MATCH query.
     * Client must format the query correctly (e.g., "`\"phrase\"`" or "`prefix*`").
     */
    fun searchNotes(query: String): Flow<List<Note>> = noteFtsDAO.searchNotes(query)

    suspend fun rebuildFtsIndex() = noteFtsDAO.rebuildFts()

    // ── REVISIONS ────────────────────────────────────

    fun getRevisionsForNote(noteId: String): Flow<List<NoteRevision>> =
        noteRevisionDAO.getRevisionsForNoteFlow(noteId)

    suspend fun saveRevision(note: Note, description: String? = null) {
        val revision = NoteRevision(
            noteId = note.id,
            contentSnapshot = note.content,
            revisionDescription = description
        )
        noteRevisionDAO.insertRevision(revision)
    }

    suspend fun restoreRevision(revision: NoteRevision) {
        noteDAO.getNoteById(revision.noteId)?.let { note ->
            noteDAO.updateNoteContent(
                noteId = note.id,
                title = note.title,
                content = revision.contentSnapshot,
                wordCount = countWords(revision.contentSnapshot)
            )
        }
    }

    // ── NOTE LINKS ────────────────────────────────────

    fun getOutgoingLinks(noteId: String): Flow<List<NoteLinkCrossRef>> =
        noteLinkDAO.getOutgoingLinksFlow(noteId)

    fun getIncomingLinks(noteId: String): Flow<List<NoteLinkCrossRef>> =
        noteLinkDAO.getIncomingLinksFlow(noteId)

    suspend fun createLink(sourceNoteId: String, targetNoteId: String) {
        val link = NoteLinkCrossRef(sourceNoteId = sourceNoteId, targetNoteId = targetNoteId)
        noteLinkDAO.insertLink(link)
    }

    suspend fun deleteLinkBetween(sourceNoteId: String, targetNoteId: String) =
        noteLinkDAO.deleteLinkBetween(sourceNoteId, targetNoteId)

    // ── HELPERS ──────────────────────────────────────

    private fun countWords(text: String): Int {
        return text.trim().split("\\s+".toRegex()).count { it.isNotEmpty() }
    }
}