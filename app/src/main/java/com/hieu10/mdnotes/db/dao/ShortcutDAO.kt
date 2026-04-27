package com.hieu10.mdnotes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hieu10.mdnotes.db.models.Shortcut
import kotlinx.coroutines.flow.Flow

@Dao
interface ShortcutDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShortcut(shortcut: Shortcut)

    @Update
    suspend fun updateShortcut(shortcut: Shortcut)

    @Delete
    suspend fun deleteShortcut(shortcut: Shortcut)

    @Query("SELECT * FROM tb_shortcuts WHERE id = :shortcutId")
    suspend fun getShortcutById(shortcutId: String): Shortcut?

    @Query("SELECT * FROM tb_shortcuts ORDER BY position ASC")
    fun getAllShortcutsFlow(): Flow<List<Shortcut>>

    /**
     * Reorder shortcuts by updating the position of one.
     * Full reordering is usually done by updating several positions in a transaction.
     */
    @Query("UPDATE tb_shortcuts SET position = :position WHERE id = :shortcutId")
    suspend fun updateShortcutPosition(shortcutId: String, position: Int)

    @Query("DELETE FROM tb_shortcuts WHERE id = :shortcutId")
    suspend fun deleteShortcutById(shortcutId: String)
}