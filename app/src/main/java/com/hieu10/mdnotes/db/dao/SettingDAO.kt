package com.hieu10.mdnotes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hieu10.mdnotes.db.models.Setting
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDAO {

    /**
     * Inserts or replaces a setting (key-value pair).
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSetting(setting: Setting)

    /**
     * Convenience: set a setting value by key.
     */
    suspend fun putSetting(key: String, value: String) {
        setSetting(Setting(key, value))
    }

    @Delete
    suspend fun deleteSetting(setting: Setting)

    @Query("SELECT value from tb_settings WHERE `key` = :key LIMIT 1")
    suspend fun getSettingValue(key: String): String?

    @Query("SELECT * FROM tb_settings WHERE `key` = :key LIMIT 1")
    suspend fun getSetting(key: String): Setting?

    @Query("SELECT * FROM tb_settings")
    fun getAllSettingsFlow(): Flow<List<Setting>>
}