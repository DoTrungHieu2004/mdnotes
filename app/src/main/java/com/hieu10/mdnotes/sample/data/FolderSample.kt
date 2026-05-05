package com.hieu10.mdnotes.sample.data

import com.hieu10.mdnotes.db.models.Folder
import java.util.UUID

val singleFolderSample = Folder(
    id = UUID.randomUUID().toString(),
    name = "Sample Folder",
    colorHex = "#FF5733",
    createdAt = System.currentTimeMillis()
)

val folderSamples = listOf(
    Folder(name = "Work Projects", colorHex = "#4285F4"),
    Folder(name = "Personal Photos", colorHex = "#EA4335"),
    Folder(name = "Tax Documents 2025", colorHex = "#FBBC04"),
    Folder(name = "Travel Plans", colorHex = "#34A853"),
    Folder(name = "Music Library", colorHex = "#8E44AD"),
    Folder(name = "System Logs", colorHex = "#2C3E50")
)