package com.hieu10.mdnotes.di

import com.hieu10.mdnotes.MDNotesApp
import com.hieu10.mdnotes.db.AppDB
import com.hieu10.mdnotes.db.repositories.FolderRepository
import com.hieu10.mdnotes.db.repositories.NoteRepository
import com.hieu10.mdnotes.db.repositories.TagRepository

class AppContainer(private val app: MDNotesApp) {

    // Database
    val database: AppDB = app.database

    // Repositories
    val folderRepository: FolderRepository by lazy {
        FolderRepository(database.folderDAO())
    }

    val noteRepository: NoteRepository by lazy {
        NoteRepository(
            noteDAO = database.noteDAO(),
            noteFtsDAO = database.noteFtsDAO(),
            noteRevisionDAO = database.noteRevisionDAO(),
            noteLinkDAO = database.noteLinkDAO()
        )
    }

    val tagRepository: TagRepository by lazy {
        TagRepository(
            tagDAO = database.tagDAO(),
            noteTagDAO = database.noteTagDAO()
        )
    }
}