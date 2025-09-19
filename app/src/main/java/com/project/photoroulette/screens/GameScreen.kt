package com.alexhekmat.photoroulette.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexhekmat.photoroulette.model.GameState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GameScreen(
    gameState: GameState,
    onPlaylistSelected: (String) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    val selectedPlaylistColor = Color(0xFF64B5F6)
    val defaultButtonColor = Color(0xFFE0E0E0)
    val purpleGradientStart = Color(0xFF9A5BB2)
    val purpleGradientEnd = Color(0xFF663399)
    val purpleCardColor = Color(0xFF7E57C2)

    // Track selected playlist
    var selectedPlaylist by remember { mutableStateOf("") }

    // Keep track of loaded game states to prevent flashing
    val currentGameState = remember { mutableStateOf<GameState.Playing?>(null) }

    // Update current game state when a new playing state arrives
    LaunchedEffect(gameState) {
        if (gameState is GameState.Playing) {
            currentGameState.value = gameState
            selectedPlaylist = ""
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(purpleGradientStart, purpleGradientEnd)
                )
            )
    ) {
        when (gameState) {
            is GameState.Loading -> {
                // Loading state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            is GameState.Error -> {
                // Error state
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Oops! Something went wrong",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = gameState.errorMessage,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    Button(
                        onClick = { onRetry() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = defaultButtonColor),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Retry",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Try Again",
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            is GameState.Playing -> {
                // Update current game state when a new playing state arrives
                currentGameState.value = gameState

                // Playing state - show the game UI
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Round indicator
                    Text(
                        text = "Round ${gameState.currentRound} of ${gameState.totalRounds}",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    // Album image and song info
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = purpleCardColor,
                        elevation = 4.dp
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))

                                // Album art with Glide - using key to force recomposition
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .weight(1f, fill = false)
                                        .aspectRatio(1f)
                                        .fillMaxWidth(0.9f)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Load image using Glide with unique key for each round
                                    key(gameState.albumArtUrl, gameState.currentRound) {
                                        GlideImage(
                                            model = gameState.albumArtUrl,
                                            contentDescription = "Album Cover",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize(),
                                            // Show a placeholder while loading
                                            loading = placeholder {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .background(Color.LightGray),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    CircularProgressIndicator(
                                                        color = Color.White,
                                                        modifier = Modifier.size(48.dp)
                                                    )
                                                }
                                            },
                                            // Show a fallback icon if the image fails to load
                                            failure = placeholder {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .background(Color(0xFF5E35B1)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.MusicNote,
                                                        contentDescription = null,
                                                        tint = Color.White,
                                                        modifier = Modifier.size(48.dp)
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                        .padding(bottom = 16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    // Song Title
                                    Text(
                                        text = gameState.songTitle,
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    // Artist
                                    Text(
                                        text = gameState.songArtist,
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Which playlist contains this song?",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Make playlists scrollable
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(gameState.playlists) { playlist ->
                            Button(
                                onClick = {
                                    selectedPlaylist = playlist
                                    onPlaylistSelected(selectedPlaylist)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(vertical = 4.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (selectedPlaylist == playlist)
                                        selectedPlaylistColor else defaultButtonColor
                                ),
                                shape = RoundedCornerShape(32.dp)
                            ) {
                                Text(
                                    text = playlist,
                                    fontSize = 18.sp,
                                    color = Color.Black,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            else -> {
                val lastGameState = currentGameState.value
                if (lastGameState != null) {
                    // Show the last known game state UI
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Round indicator
                        Text(
                            text = "Round ${lastGameState.currentRound} of ${lastGameState.totalRounds}",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )

                        // Album image and song info
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            shape = RoundedCornerShape(8.dp),
                            backgroundColor = purpleCardColor,
                            elevation = 4.dp
                        ) {
                            // Show last known content to prevent flashing
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Processing...",
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                } else {
                    // Fallback if no game state is available
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Loading game...",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}