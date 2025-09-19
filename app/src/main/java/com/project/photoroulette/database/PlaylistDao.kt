package com.alexhekmat.photoroulette.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Dao for the Playlists table.
 */
@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(playlists: List<PlaylistEntity>)

    @Query("SELECT * FROM playlists")
    fun getAllPlaylists(): List<PlaylistEntity>
}