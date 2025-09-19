package com.alexhekmat.photoroulette.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuScreen(
    onStartGameClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    // Gradient Colors
    val lightPurple = Color(0xFF9A5BB2) // Top color
    val darkPurple = Color(0xFF663399)  // Bottom color
    val grayButtonColor = Color(0xFFE0E0E0)

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
            Spacer(modifier = Modifier.height(120.dp))

            // Title text
            Text(
                text = "Playlist\nRoulette",
                fontSize = 40.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 120.dp)
            )

            // Start Game Button
            Button(
                onClick = { onStartGameClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = grayButtonColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Create Game",
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }

            // Buffer space between buttons
            Spacer(modifier = Modifier.height(48.dp))

            // Settings Button
            Button(
                onClick = { onSettingsClick() },
                modifier = Modifier
                    .width(180.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = grayButtonColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Settings",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }

            // Flexible space to push content up
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}