package com.alexhekmat.photoroulette.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for handling login logic and state
 */
class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Updates the username in the state
     */
    fun updateUsername(username: String) {
        _uiState.update { currentState ->
            currentState.copy(
                username = username,
                showError = false
            )
        }
    }

    /**
     * Updates the password in the state
     */
    fun updatePassword(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                showError = false
            )
        }
    }

    /**
     * Attempts to login with the current credentials
     */
    fun login(): Boolean {
        val isValid = _uiState.value.username == "admin" && _uiState.value.password == "admin"
        if (isValid) {
            _uiState.update { currentState ->
                currentState.copy(
                    username = "",
                    password = "",
                    showError = false
                )
            }
            return true
        } else {
            _uiState.update { currentState ->
                currentState.copy(showError = true)
            }
            return false
        }
    }

    /**
     * Data class representing the UI state for login
     */
    data class LoginUiState(
        val username: String = "",
        val password: String = "",
        val showError: Boolean = false
    )

    /**
     * Factory for creating LoginViewModel
     */
    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}