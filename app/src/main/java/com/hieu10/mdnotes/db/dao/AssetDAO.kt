package com.hieu10.mdnotes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hieu10.mdnotes.db.models.Asset
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: Asset)

    @Update
    suspend fun updateAsset(asset: Asset)

    @Delete
    suspend fun deleteAsset(asset: Asset)

    @Query("SELECT * FROM tb_assets WHERE assetId = :assetId")
    suspend fun getAssetById(assetId: String): Asset?

    @Query("SELECT * FROM tb_assets WHERE noteId = :noteId ORDER BY fileName ASC")
    fun getAssetsForNoteFlow(noteId: String): Flow<List<Asset>>

    @Query("DELETE FROM tb_assets WHERE assetId = :assetId")
    suspend fun deleteAssetById(assetId: String)

    @Query("DELETE FROM tb_assets WHERE noteId = :noteId")
    suspend fun deleteAllAssetsForNote(noteId: String)
}