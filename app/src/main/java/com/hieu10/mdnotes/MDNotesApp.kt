package com.hieu10.mdnotes

import android.app.Application
import androidx.room.Room
import com.hieu10.mdnotes.db.AppDB
import com.hieu10.mdnotes.di.AppContainer

class MDNotesApp : Application() {
    lateinit var database: AppDB
        private set

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDB::class.java,
            "mdnotes.db"
        ).build()

        container = AppContainer(this)
    }
}