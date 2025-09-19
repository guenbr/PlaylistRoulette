package com.alexhekmat.photoroulette.model

import android.content.Context
import android.util.Log
import com.alexhekmat.photoroulette.database.AppDatabaseProvider
import com.alexhekmat.photoroulette.database.PlaylistEntity
import com.alexhekmat.photoroulette.database.TrackEntity
import com.alexhekmat.photoroulette.model.network.PlaylistResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * PlaylistModel class for loading playlists from the local JSON file
 */
class PlaylistModel(private val context: Context) {
    private val TAG = "PlaylistModel"

    fun getLocalPlaylists(): List<PlaylistResponse> {
        return try {
            val json = context.assets.open("playlists.json").bufferedReader().use { it.readText() }
            val playlistListType = object : TypeToken<List<PlaylistResponse>>() {}.type
            val playlists = Gson().fromJson<List<PlaylistResponse>>(json, playlistListType)
            val playlistDao = AppDatabaseProvider.playlistDao(context)
            val trackDao = AppDatabaseProvider.trackDao(context)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val playlistEntities = playlists.map { it.toEntity() }
                    val allTracks = playlists.flatMap { it.toTrackEntities() }
                    playlistDao.insertAll(playlistEntities)
                    trackDao.insertAll(allTracks)
                    Log.d(TAG, "Saved ${playlistEntities.size} playlists and ${allTracks.size} tracks")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to insert into Room DB", e)
                }
            }

            playlists
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error loading playlists from local JSON", e)
            emptyList()
        }
    }

    fun PlaylistResponse.toEntity(): PlaylistEntity {
        return PlaylistEntity(
            id = this.id,
            name = this.name,
            image = this.image,
            tracksCount = this.tracks.size
        )
    }

    fun PlaylistResponse.toTrackEntities(): List<TrackEntity> {
        return tracks.mapNotNull { track ->
            if (track.trackId != null) {
                TrackEntity(
                    id = track.trackId,
                    name = track.trackName,
                    artist = track.artistName,
                    album = track.albumName,
                    image = track.trackImage,
                    playlistId = this.id
                )
            } else {
                null
            }
        }
    }
}