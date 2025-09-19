package com.alexhekmat.photoroulette.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alexhekmat.photoroulette.PlaylistRouletteApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "SettingsViewModel"
private const val PREFS_NAME = "playlist_roulette_prefs"
private const val KEY_API_URL = "api_url"
private const val KEY_API_TOKEN = "api_token"
private const val DEFAULT_API_URL = "https://ahek.pythonanywhere.com"

/**
 * ViewModel for managing API configuration and connection status
 */
class SettingsViewModel(private val context: Context) : ViewModel() {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val _apiUrl = MutableStateFlow(getStoredApiUrl())
    val apiUrl: StateFlow<String> = _apiUrl.asStateFlow()
    private val _apiToken = MutableStateFlow(getStoredApiToken())
    val apiToken: StateFlow<String> = _apiToken.asStateFlow()
    private val _connectionStatus = MutableStateFlow<ConnectionStatus>(ConnectionStatus.NotConnected)
    val connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus

    init {
        checkConnectionStatus()
    }

    /**
     * Update url and select token
     */
    fun updateApiUrlAndExtractToken(url: String): Boolean {
        if (url.isEmpty()) {
            _connectionStatus.value = ConnectionStatus.Error("URL cannot be empty")
            return false
        }
        val urlParts = url.split("?", limit = 2)
        if (urlParts.size > 1) {
            val hasToken = urlParts[1].contains("token=")
        }

        try {
            val baseUrl = if (url.contains("?")) {
                url.substringBefore("?")
            } else {
                url
            }

            if (!baseUrl.contains("ahek.pythonanywhere.com")) {
                _connectionStatus.value = ConnectionStatus.Error("Invalid URL - must contain ahek.pythonanywhere.com")
                return false
            }

            if (url.contains("token=")) {
                val tokenParam = url.substringAfter("token=")
                val token = if (tokenParam.contains("&")) {
                    tokenParam.substringBefore("&")
                } else {
                    tokenParam
                }

                if (token.isNotEmpty()) {
                    _apiToken.value = token
                    saveApiToken(token)
                    _connectionStatus.value = ConnectionStatus.Connected
                } else {
                    _connectionStatus.value = ConnectionStatus.Error("Token is empty")
                    return false
                }
            } else {
                _connectionStatus.value = ConnectionStatus.Error("No token found in URL")
                return false
            }
            _apiUrl.value = baseUrl
            saveApiUrl(baseUrl)
            (context.applicationContext as? PlaylistRouletteApplication)?.refreshRepository()

            return true
        } catch (e: Exception) {
            _connectionStatus.value = ConnectionStatus.Error("Error processing URL: ${e.message}")
            return false
        }
    }

    /**
     * Check if API configuration is valid
     */
    fun hasValidApiConfig(): Boolean {
        val hasConfig = _apiUrl.value.isNotEmpty() && _apiToken.value.isNotEmpty()
        return hasConfig
    }

    /**
     * Check and update the connection status based on saved credentials
     */
    private fun checkConnectionStatus() {
        if (hasValidApiConfig()) {
            _connectionStatus.value = ConnectionStatus.Connected
        } else {
            _connectionStatus.value = ConnectionStatus.NotConnected
        }
    }

    /**
     * Retrieves stored api url
     */
    private fun getStoredApiUrl(): String {
        val url = prefs.getString(KEY_API_URL, DEFAULT_API_URL) ?: DEFAULT_API_URL
        return url
    }

    /**
     * Retrieves stored api token
     */
    private fun getStoredApiToken(): String {
        val token = prefs.getString(KEY_API_TOKEN, "") ?: ""
        return token
    }

    /**
     * Saves api url
     */
    private fun saveApiUrl(url: String) {
        viewModelScope.launch {
            prefs.edit().putString(KEY_API_URL, url).apply()
        }


    }

    /**
     * Saves API token
     */
    private fun saveApiToken(token: String) {
        viewModelScope.launch {
            prefs.edit().putString(KEY_API_TOKEN, token).apply()
        }
    }

    /**
     * Connection status to track API configuration state
     */
    sealed class ConnectionStatus {
        object NotConnected : ConnectionStatus()
        object Connected : ConnectionStatus()
        data class Error(val message: String) : ConnectionStatus()
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                return SettingsViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}