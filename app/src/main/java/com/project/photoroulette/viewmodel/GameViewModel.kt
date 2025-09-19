package com.alexhekmat.photoroulette.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alexhekmat.photoroulette.model.GameState
import com.alexhekmat.photoroulette.model.Song
import com.alexhekmat.photoroulette.model.network.ApiResult
import com.alexhekmat.photoroulette.repository.PlaylistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Game ViewModel
 */
class GameViewModel(private val repository: PlaylistRepository) : ViewModel() {
    private val TAG = "GameViewModel"
    private val _gameState = MutableStateFlow<GameState>(GameState.Loading)
    val gameState: StateFlow<GameState> = _gameState
    private var currentRound = 1
    private var totalRounds = 5
    private var correctAnswers = 0
    private val userSelections = mutableMapOf<Int, String>()
    private val correctPlaylists = mutableMapOf<Int, String>()
    private val songsForRounds = mutableMapOf<Int, Song.BasicSong>()
    private val _playlists = mutableListOf<String>()
    private val _songPlaylistPairs = mutableMapOf<Song.BasicSong, String>()
    private val NUMBER_OF_OPTIONS = 4

    /**
     * Initialize a singleplayer game with the specified number of rounds.
     */
    fun initializeSinglePlayerGame(roundCount: Int) {
        totalRounds = roundCount
        currentRound = 1
        correctAnswers = 0
        userSelections.clear()
        correctPlaylists.clear()
        songsForRounds.clear()
        _playlists.clear()
        _songPlaylistPairs.clear()

        loadAllPlaylists()
    }

    /**
     * Load all the playlists
     */
    private fun loadAllPlaylists() {
        viewModelScope.launch {
            _gameState.value = GameState.Loading

            repository.getPlaylists().collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val allPlaylistNames = result.data.map { it.name }
                        _playlists.clear()
                        _playlists.addAll(allPlaylistNames)
                        loadGameData()
                    }
                    is ApiResult.Error -> {
                        _gameState.value = GameState.Error(
                            errorMessage = "Failed to load playlists: ${result.errorMessage}"
                        )
                    }
                    is ApiResult.Loading -> {
                        _gameState.value = GameState.Loading
                    }
                }
            }
        }
    }

    /**
     * Load API game data or fallback
     */
    private fun loadGameData() {
        viewModelScope.launch {
            _gameState.value = GameState.Loading
            repository.getSongsWithPlaylists(totalRounds).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        _songPlaylistPairs.clear()
                        _songPlaylistPairs.putAll(result.data)
                        setupRounds()
                        startGameRound()
                    }
                    is ApiResult.Error -> {
                        _gameState.value = GameState.Error(
                            errorMessage = result.errorMessage
                        )
                    }
                    is ApiResult.Loading -> {
                        _gameState.value = GameState.Loading
                    }
                }
            }
        }
    }

    /**
     * Set up each round for the game, song and its corresponding playlist
     */
    private fun setupRounds() {
        val songEntries = _songPlaylistPairs.entries.toList().shuffled()
        for (i in 1..totalRounds) {
            if (i <= songEntries.size) {
                val entry = songEntries[i - 1]
                songsForRounds[i] = entry.key
                correctPlaylists[i] = entry.value
            } else {
                Log.w(TAG, "Not enough songs")
            }
        }
    }

    /**
     * Get the playlist options for the current round.
     */
    private fun getPlaylistOptions(round: Int): List<String> {
        val correctPlaylist = correctPlaylists[round] ?: return emptyList()
        val currentSong = songsForRounds[round]
        val playlistsWithSameSong = mutableSetOf<String>()
        _songPlaylistPairs.forEach { (song, playlist) ->
            if (song.id == currentSong?.id ||
                (song.title == currentSong?.title && song.artist == currentSong?.artist)) {
                playlistsWithSameSong.add(playlist)
            }
        }
        val options = mutableListOf<String>()
        options.add(correctPlaylist)
        val preferredPlaylists = _playlists.filter { playlist ->
            playlist != correctPlaylist &&
                    !options.contains(playlist) &&
                    !(playlistsWithSameSong.contains(playlist) && playlist != correctPlaylist)
        }.distinct().shuffled()
        val preferredCount = minOf(NUMBER_OF_OPTIONS - 1, preferredPlaylists.size)
        options.addAll(preferredPlaylists.take(preferredCount))

        if (options.size < NUMBER_OF_OPTIONS) {
            val fallbackPlaylists = _playlists.filter { playlist ->
                playlist != correctPlaylist &&
                        !options.contains(playlist)
            }.distinct().shuffled()

            val neededCount = NUMBER_OF_OPTIONS - options.size
            val additionalCount = minOf(neededCount, fallbackPlaylists.size)
            options.addAll(fallbackPlaylists.take(additionalCount))
        }
        return options
    }

    /**
     * Start the next round of the game.
     */
    private fun startGameRound() {
        if (currentRound <= totalRounds && currentRound <= songsForRounds.size) {
            val song = songsForRounds[currentRound]
            if (song != null) {
                val playlistOptions = getPlaylistOptions(currentRound)

                _gameState.value = GameState.Playing(
                    currentRound = currentRound,
                    totalRounds = totalRounds,
                    songTitle = song.title,
                    songArtist = song.artist,
                    albumArtUrl = song.imageUrl ?: "",
                    playlists = playlistOptions
                )
            } else {
                _gameState.value = GameState.Error("No song found for round $currentRound")
            }
        } else {
            _gameState.value = GameState.GameOver(
                correctAnswers = correctAnswers,
                totalRounds = totalRounds
            )
        }
    }

    /**
     * Function for handling the user's answer
     */
    fun onPlaylistSelected(playlist: String) {
        userSelections[currentRound] = playlist
        val correctPlaylist = correctPlaylists[currentRound]
        val isCorrect = playlist == correctPlaylist
        if (isCorrect) {
            correctAnswers++
        }
        val song = songsForRounds[currentRound]

        if (song != null && correctPlaylist != null) {
            _gameState.value = GameState.RoundResult(
                currentRound = currentRound,
                totalRounds = totalRounds,
                songTitle = song.title,
                songArtist = song.artist,
                albumArtUrl = song.imageUrl ?: "",
                correctPlaylist = correctPlaylist,
                selectedPlaylist = playlist
            )
        } else {
            _gameState.value = GameState.Error("Missing song or playlist data")
        }
    }

    /**
     * Proceed to the next round of the game.
     */
    fun proceedToNextRound() {
        currentRound++
        startGameRound()
    }

    /**
     * Retries game initialization in case of an error
     */
    fun retry() {
        loadAllPlaylists()
    }

    /**
     * GameVewModel Factory class
     */
    class Factory(private val repository: PlaylistRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
                return GameViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}