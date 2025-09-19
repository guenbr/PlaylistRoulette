package com.alexhekmat.photoroulette.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alexhekmat.photoroulette.model.network.ApiResult
import com.alexhekmat.photoroulette.repository.PlaylistRepository
import com.alexhekmat.photoroulette.screens.PlaylistItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the SelectPlaylistScreen
 */
class SelectPlaylistScreenViewModel(private val repository: PlaylistRepository) : ViewModel() {
    private val TAG = "PlaylistScreenVM"
    private val _uiState = MutableStateFlow<PlaylistScreenUiState>(PlaylistScreenUiState.Loading)
    val uiState: StateFlow<PlaylistScreenUiState> = _uiState

    init {
        loadPlaylists()
    }

    /**
     * Loads the playlists from the repository
     */
    private fun loadPlaylists() {
        viewModelScope.launch {
            _uiState.value = PlaylistScreenUiState.Loading

            repository.getPlaylists().collect { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _uiState.value = PlaylistScreenUiState.Loading
                    }
                    is ApiResult.Success -> {
                        val playlists = result.data
                        if (playlists.isNotEmpty()) {
                            val playlistItems = playlists.map { playlist ->
                                PlaylistItem(
                                    id = playlist.id,
                                    name = playlist.name,
                                    tracksCount = playlist.tracksCount,
                                    imageUrl = playlist.imageUrl
                                )
                            }
                            _uiState.value = PlaylistScreenUiState.Success(playlistItems)
                        } else {
                            _uiState.value = PlaylistScreenUiState.Empty
                        }
                    }
                    is ApiResult.Error -> {
                        _uiState.value = PlaylistScreenUiState.Error(result.errorMessage)
                    }
                }
            }
        }
    }

    /**
     * Retries loading playlists in case of an error
     */
    fun retry() {
        loadPlaylists()
    }

    /**
     * Represents the UI state for the playlist screen
     */
    sealed class PlaylistScreenUiState {
        object Loading : PlaylistScreenUiState()
        data class Success(val playlists: List<PlaylistItem>) : PlaylistScreenUiState()
        object Empty : PlaylistScreenUiState()
        data class Error(val message: String) : PlaylistScreenUiState()
    }

    /**
     * Factory for creating SelectPlaylistScreenViewModel
     */
    class Factory(private val repository: PlaylistRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SelectPlaylistScreenViewModel::class.java)) {
                return SelectPlaylistScreenViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}