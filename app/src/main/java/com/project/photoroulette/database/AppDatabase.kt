package com.alexhekmat.photoroulette.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The Room Database for the application.
 */
@Database(entities = [PlaylistEntity::class, TrackEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
    abstract fun trackDao(): TrackDao
}