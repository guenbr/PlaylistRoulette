package com.alexhekmat.photoroulette.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameSetupScreen(
    onViewPlaylistsClick: () -> Unit = {},
    onStartGameClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    // Gradient Colors
    val lightPurple = Color(0xFF9A5BB2) // Top color
    val darkPurple = Color(0xFF663399)  // Bottom color
    val grayButtonColor = Color(0xFFE0E0E0)
    // Color for the Start Game button
    val greenButtonColor = Color(0xFF4AE290)

    // Selected number of rounds
    var selectedRounds by remember { mutableStateOf(5) }
    // Round options
    val roundOptions = listOf(5, 8, 10)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(lightPurple, darkPurple)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Header with back button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onBackClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "Game Setup",
                    fontSize = 36.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Select number of rounds section
            Text(
                text = "Select Number of Rounds",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Round selection buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                roundOptions.forEach { rounds ->
                    Button(
                        onClick = { selectedRounds = rounds },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .padding(horizontal = 4.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (selectedRounds == rounds)
                                Color(0xFF8BC34A) else grayButtonColor
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "$rounds",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = if (selectedRounds == rounds)
                                FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // View Playlists Section
            Button(
                onClick = { onViewPlaylistsClick() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = grayButtonColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "View Playlists",
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Start Game Button
            Button(
                onClick = { onStartGameClick(selectedRounds) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = greenButtonColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Start Game",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}