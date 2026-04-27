package com.hieu10.mdnotes

import android.app.Application
import androidx.room.Room
import com.hieu10.mdnotes.db.AppDB

class MDNotesApp : Application() {
    lateinit var database: AppDB

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDB::class.java,
            "mdnotes.db"
        ).build()
    }
}