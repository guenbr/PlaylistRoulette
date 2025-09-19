package com.alexhekmat.photoroulette.network

import com.alexhekmat.photoroulette.model.network.PlaylistResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Retrofit API service interface for Playlist Roulette API calls
 */
interface PlaylistApiService {
    @GET("playlists")
    suspend fun getPlaylists(@Query("token") token: String): Response<List<PlaylistResponse>>
}