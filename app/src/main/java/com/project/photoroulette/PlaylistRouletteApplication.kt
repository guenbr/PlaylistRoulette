package com.alexhekmat.photoroulette

import android.app.Application
import android.content.Context
import android.util.Log
import com.alexhekmat.photoroulette.repository.PlaylistRepository

/**
 * Application class for the app
 */
class PlaylistRouletteApplication : Application() {
    companion object {
        private const val TAG = "PlaylistRouletteApp"
        private const val PREFS_NAME = "playlist_roulette_prefs"
        private const val KEY_API_URL = "api_url"
        private const val KEY_API_TOKEN = "api_token"
        private const val DEFAULT_API_URL = "https://ahek.pythonanywhere.com"
    }

    private var _repository: PlaylistRepository? = null

    val repository: PlaylistRepository
        get() {
            if (_repository == null) {
                _repository = createRepository()
            }
            return _repository!!
        }

    /**
     * Refresh the repository with latest settings
     */
    fun refreshRepository() {
        _repository = createRepository()
    }

    /**
     * Create a new instance of the repository
     */
    private fun createRepository(): PlaylistRepository {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val apiUrl = prefs.getString(KEY_API_URL, DEFAULT_API_URL) ?: DEFAULT_API_URL
        val apiToken = prefs.getString(KEY_API_TOKEN, "") ?: ""

        return PlaylistRepository(apiUrl, apiToken, applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        repository
    }
}