package com.hieu10.mdnotes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hieu10.mdnotes.db.converters.Converters
import com.hieu10.mdnotes.db.dao.AssetDAO
import com.hieu10.mdnotes.db.dao.FolderDAO
import com.hieu10.mdnotes.db.dao.NoteDAO
import com.hieu10.mdnotes.db.dao.NoteFtsDAO
import com.hieu10.mdnotes.db.dao.NoteLinkDAO
import com.hieu10.mdnotes.db.dao.NoteRevisionDAO
import com.hieu10.mdnotes.db.dao.NoteTagDAO
import com.hieu10.mdnotes.db.dao.ReminderDAO
import com.hieu10.mdnotes.db.dao.SettingDAO
import com.hieu10.mdnotes.db.dao.ShortcutDAO
import com.hieu10.mdnotes.db.dao.TagDAO
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
    abstract fun folderDAO(): FolderDAO
    abstract fun noteDAO(): NoteDAO
    abstract fun noteFtsDAO(): NoteFtsDAO
    abstract fun tagDAO(): TagDAO
    abstract fun noteTagDAO(): NoteTagDAO
    abstract fun noteRevisionDAO(): NoteRevisionDAO
    abstract fun assetDAO(): AssetDAO
    abstract fun settingDAO(): SettingDAO
    abstract fun noteLinkDAO(): NoteLinkDAO
    abstract fun reminderDAO(): ReminderDAO
    abstract fun shortcutDAO(): ShortcutDAO
}