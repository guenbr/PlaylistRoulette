package com.alexhekmat.photoroulette.model

sealed class GameState {
    object Loading : GameState()

    data class Error(val errorMessage: String) : GameState()

    data class Playing(
        val currentRound: Int,
        val totalRounds: Int,
        val songTitle: String,
        val songArtist: String,
        val albumArtUrl: String,
        val playlists: List<String>
    ) : GameState()

    data class RoundResult(
        val currentRound: Int,
        val totalRounds: Int,
        val songTitle: String,
        val songArtist: String,
        val albumArtUrl: String,
        val correctPlaylist: String,
        val selectedPlaylist: String
    ) : GameState()

    data class GameOver(
        val correctAnswers: Int,
        val totalRounds: Int
    ) : GameState()
}