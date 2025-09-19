package com.alexhekmat.photoroulette.navigation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexhekmat.photoroulette.PlaylistRouletteApplication
import com.alexhekmat.photoroulette.model.GameState
import com.alexhekmat.photoroulette.screens.GameOverScreen
import com.alexhekmat.photoroulette.screens.GameScreen
import com.alexhekmat.photoroulette.screens.RoundResultScreen
import com.alexhekmat.photoroulette.viewmodel.GameViewModel
import com.alexhekmat.photoroulette.viewmodel.SettingsViewModel

@Composable
fun GameNavigation(
    onMainMenuReturn: () -> Unit = {},
    onPlayAgain: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    roundCount: Int,
    context: Context = LocalContext.current
) {
    // Get the application instance to access the repository
    val application = context.applicationContext as PlaylistRouletteApplication

    // Check if API config is valid using SettingsViewModel
    val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory(context))
    val hasValidConfig = settingsViewModel.hasValidApiConfig()

    if (!hasValidConfig) {
        LaunchedEffect(Unit) {
            onNavigateToSettings()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF9A5BB2), Color(0xFF663399))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Must connect to Spotify",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                CircularProgressIndicator(color = Color.White)
            }
        }
        return
    }

    // Create GameViewModel with the repository from application
    val gameViewModel: GameViewModel = viewModel(factory = GameViewModel.Factory(application.repository))

    // Start the game with the specified number of rounds
    LaunchedEffect(key1 = roundCount) {
        gameViewModel.initializeSinglePlayerGame(roundCount)
    }

    // Collect the current game state
    val gameState by gameViewModel.gameState.collectAsState()

    // Show different screens based on the game state
    when (val state = gameState) {
        is GameState.Loading -> {
            LoadingScreen()
        }

        is GameState.Error -> {
            // throw error
            return
        }

        is GameState.Playing -> {
            GameScreen(
                gameState = state,
                onPlaylistSelected = { playlist ->
                    gameViewModel.onPlaylistSelected(playlist)
                },
                onRetry = { gameViewModel.retry() }
            )
        }

        is GameState.RoundResult -> {
            RoundResultScreen(
                currentRound = state.currentRound,
                totalRounds = state.totalRounds,
                songTitle = state.songTitle,
                songArtist = state.songArtist,
                albumArtUrl = state.albumArtUrl,
                correctPlaylist = state.correctPlaylist,
                selectedPlaylist = state.selectedPlaylist,
                onNextRound = {
                    gameViewModel.proceedToNextRound()
                }
            )
        }

        is GameState.GameOver -> {
            GameOverScreen(
                correctAnswers = state.correctAnswers,
                totalRounds = state.totalRounds,
                onPlayAgainClick = onPlayAgain,
                onMainMenuClick = onMainMenuReturn
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF9A5BB2), Color(0xFF663399))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}