package com.alexhekmat.photoroulette.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * PlaylistEntity within the Playlists table.
 */
@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey val id: String,
    val name: String,
    val image: String?,
    val tracksCount: Int = 0
)