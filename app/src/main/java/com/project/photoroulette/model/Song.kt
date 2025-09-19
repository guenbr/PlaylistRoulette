package com.alexhekmat.photoroulette.model

/**
 * Minimal song set up
 */
sealed class Song {
    data class BasicSong(
        val id: String,
        val title: String,
        val artist: String,
        val album: String,
        val imageUrl: String? = null
    ): Song()
}