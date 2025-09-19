package com.alexhekmat.photoroulette.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameOverScreen(
    correctAnswers: Int = 3,
    totalRounds: Int = 5,
    onPlayAgainClick: () -> Unit = {},
    onMainMenuClick: () -> Unit = {}
) {
    val purpleGradientStart = Color(0xFF9A5BB2)
    val purpleGradientEnd = Color(0xFF663399)
    val grayButtonColor = Color(0xFFE0E0E0)

    // Calculate percentage score
    val scorePercentage = (correctAnswers.toFloat() / totalRounds.toFloat() * 100).toInt()

    // Determine performance message
    val performanceMessage = when {
        scorePercentage >= 80 -> "Excellent!"
        scorePercentage >= 60 -> "Good Job!"
        scorePercentage >= 40 -> "Not Bad!"
        else -> "Keep Practicing!"
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Game Over",
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            // Score display
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color(0xFF7E57C2),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = performanceMessage,
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Text(
                        text = "$correctAnswers/$totalRounds",
                        color = Color.White,
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Correct Answers",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Score percentage
                    Text(
                        text = "$scorePercentage%",
                        color = Color.White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Score",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Play Again button
            Button(
                onClick = onPlayAgainClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = grayButtonColor
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Play Again",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Menu button
            Button(
                onClick = onMainMenuClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = grayButtonColor
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Main Menu",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }
    }
}