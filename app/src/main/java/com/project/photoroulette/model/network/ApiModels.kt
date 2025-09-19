package com.alexhekmat.photoroulette.model.network

import com.alexhekmat.photoroulette.model.Playlist
import com.alexhekmat.photoroulette.model.Song
import com.google.gson.annotations.SerializedName


data class PlaylistResponse(
    val id: String,
    val name: String,
    val image: String?,
    val tracks: List<TrackResponse> = emptyList()
)

data class TrackResponse(
    @SerializedName("track_id") val trackId: String,
    @SerializedName("track_name") val trackName: String,
    @SerializedName("artist_name") val artistName: String,
    @SerializedName("album_name") val albumName: String,
    @SerializedName("track_image") val trackImage: String
)

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val errorMessage: String, val code: Int = 0) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

fun PlaylistResponse.toDomainModel(): Playlist.BasicPlaylist {
    return Playlist.BasicPlaylist(
        id = this.id,
        name = this.name,
        imageUrl = this.image ?: "",
        tracksCount = this.tracks.size
    )
}

fun TrackResponse.toDomainModel(): Song.BasicSong {
    return Song.BasicSong(
        id = this.trackId,
        title = this.trackName,
        artist = this.artistName,
        album = this.albumName,
        imageUrl = this.trackImage
    )
}

fun List<PlaylistResponse>.toDomainModels(): List<Playlist.BasicPlaylist> {
    return this.map { it.toDomainModel() }
}