package com.alexhekmat.photoroulette.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EnterGamePinScreen(
    onJoinClick: (String) -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    // Gradient Colors
    val lightPurple = Color(0xFF9A5BB2) // Top color
    val darkPurple = Color(0xFF663399)  // Bottom color

    val grayButtonColor = Color(0xFFE0E0E0)

    // Default empty pin
    var gamePin by remember { mutableStateOf("") }

    val isPinValid = gamePin.length == 6 && gamePin.all { it.isDigit() }

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
                text = "Enter Game Pin",
                fontSize = 40.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 80.dp)
            )

            // Game pin input field
            TextField(
                value = gamePin,
                onValueChange = {
                    // Pin must be 6 digits
                    if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                        gamePin = it
                    }
                },
                label = { Text("Enter 6-Digit Code", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = grayButtonColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black
                ),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    letterSpacing = 8.sp
                ),
                placeholder = {
                    Text(
                        text = "- - - - - -",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(8.dp)
            )

            Button(
                onClick = { onJoinClick(gamePin) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = grayButtonColor
                ),
                shape = RoundedCornerShape(8.dp),
//                enabled = gamePin.length == 6
                enabled = isPinValid
            ) {
                Text(
                    text = "Join",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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