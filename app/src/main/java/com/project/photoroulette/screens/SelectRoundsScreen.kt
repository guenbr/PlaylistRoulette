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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectRoundsScreen(
    onRoundsSelected: (Int) -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    // Gradient Colors
    val lightPurple = Color(0xFF9A5BB2) // Top color
    val darkPurple = Color(0xFF663399)  // Bottom color

    val grayButtonColor = Color(0xFFE0E0E0)

    // Default 5 rounds
    var selectedRounds by remember { mutableIntStateOf(5) }

    // Default round options
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
            Spacer(modifier = Modifier.height(120.dp))

            // Title
            Text(
                text = "Select Rounds",
                fontSize = 40.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 80.dp)
            )

            // Dynamic
            for (rounds in roundOptions) {
                Button(
                    onClick = {
                        selectedRounds = rounds
                        onRoundsSelected(rounds)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = grayButtonColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "$rounds rounds",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = { onCancelClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = grayButtonColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Cancel",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
}