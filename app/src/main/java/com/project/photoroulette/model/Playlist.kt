package com.alexhekmat.photoroulette.model

/**
 * A Spotify (or local) playlist that can be used in a game.
 */
sealed class Playlist {
    data class BasicPlaylist(
        val id: String,
        val name: String,
        val imageUrl: String = "",
        val tracksCount: Int
    ): Playlist()
}