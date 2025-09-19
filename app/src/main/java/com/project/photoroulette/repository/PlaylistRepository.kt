package com.alexhekmat.photoroulette.repository

import android.content.Context
import android.util.Log
import com.alexhekmat.photoroulette.model.Playlist
import com.alexhekmat.photoroulette.model.PlaylistModel
import com.alexhekmat.photoroulette.model.Song
import com.alexhekmat.photoroulette.model.network.ApiResult
import com.alexhekmat.photoroulette.model.network.toDomainModel
import com.alexhekmat.photoroulette.model.network.toDomainModels
import com.alexhekmat.photoroulette.network.PlaylistApiService
import com.alexhekmat.photoroulette.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Repository for handling playlist data from API with local JSON fallback
 */
class PlaylistRepository(
    private val baseUrl: String,
    private val token: String,
    private val context: Context
) {
    private val TAG = "PlaylistRepository"
    private val apiService: PlaylistApiService by lazy {
        RetrofitClient.createPlaylistApiService(baseUrl)
    }
    private val playlistModel = PlaylistModel(context)

    fun getPlaylists(): Flow<ApiResult<List<Playlist.BasicPlaylist>>> = flow {
        emit(ApiResult.Loading)
        try {
            val localPlaylists = playlistModel.getLocalPlaylists()

            if (localPlaylists.isNotEmpty()) {
                val playlists = localPlaylists.toDomainModels()
                emit(ApiResult.Success(playlists))
            } else {
                emit(ApiResult.Error("No playlists found in local JSON"))
            }

        } catch (e: Exception) {
            emit(ApiResult.Error("Error: Unknown error"))
        }
    }.flowOn(Dispatchers.IO)


    /**
     * Get songs with their playlists for the game
     * Uses API if available, falls back to local JSON
     */
    fun getSongsWithPlaylists(count: Int): Flow<ApiResult<Map<Song.BasicSong, String>>> = flow {
        emit(ApiResult.Loading)
        val localPlaylists = playlistModel.getLocalPlaylists()
        val songPlaylistMap = mutableMapOf<Song.BasicSong, String>()
        localPlaylists.forEach { playlist ->
            if (playlist.tracks.isNotEmpty()) {
                playlist.tracks.forEach { track ->
                    try {
                        val song = track.toDomainModel()
                        songPlaylistMap[song] = playlist.name
                    } catch (e: Exception) {
                        Log.e(TAG, "Error mapping track to song model from local JSON", e)
                    }
                }
            }
        }
        if (songPlaylistMap.isNotEmpty()) {
            val shuffledEntries = songPlaylistMap.entries.shuffled()
            val resultCount = minOf(count, shuffledEntries.size)
            val result = shuffledEntries.take(resultCount).associate { it.toPair() }
            emit(ApiResult.Success(result))
        } else {
            emit(ApiResult.Error("No songs found"))
        }
    }.flowOn(Dispatchers.IO)
}