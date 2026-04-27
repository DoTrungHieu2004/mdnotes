package com.hieu10.mdnotes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hieu10.mdnotes.db.converters.Converters
import com.hieu10.mdnotes.db.models.Asset
import com.hieu10.mdnotes.db.models.Folder
import com.hieu10.mdnotes.db.models.Note
import com.hieu10.mdnotes.db.models.NoteFTS
import com.hieu10.mdnotes.db.models.NoteLinkCrossRef
import com.hieu10.mdnotes.db.models.NoteRevision
import com.hieu10.mdnotes.db.models.NoteTagCrossRef
import com.hieu10.mdnotes.db.models.Reminder
import com.hieu10.mdnotes.db.models.Setting
import com.hieu10.mdnotes.db.models.Shortcut
import com.hieu10.mdnotes.db.models.Tag

@Database(
    entities = [
        Folder::class, Note::class, NoteFTS::class, Tag::class, NoteTagCrossRef::class,
        NoteRevision::class, Asset::class, Setting::class, NoteLinkCrossRef::class, Reminder::class,
        Shortcut::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDB : RoomDatabase() {

}