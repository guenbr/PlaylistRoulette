package com.alexhekmat.photoroulette.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * TrackEntity within the tracks table.
 */
@Entity(
    tableName = "tracks",
    foreignKeys = [ForeignKey(
        entity = PlaylistEntity::class,
        parentColumns = ["id"],
        childColumns = ["playlistId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TrackEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val artist: String?,
    val album: String?,
    val image: String?,
    val playlistId: String
)