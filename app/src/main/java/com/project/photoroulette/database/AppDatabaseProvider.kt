package com.alexhekmat.photoroulette.database

import android.content.Context
import androidx.room.Room

/**
 * AppDatabaseProvider for the entire application to access the database.
 */
object AppDatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext, // ✅ safest
                AppDatabase::class.java,
                "app-db"
            ).build()
            INSTANCE = instance
            instance
        }
    }

    fun playlistDao(context: Context): PlaylistDao = getDatabase(context).playlistDao()
    fun trackDao(context: Context): TrackDao = getDatabase(context).trackDao()
}