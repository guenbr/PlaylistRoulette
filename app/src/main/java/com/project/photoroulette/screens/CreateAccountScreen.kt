package com.alexhekmat.photoroulette.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onBackToLogin: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Gradient colors
    val lightPurple = Color(0xFF9A5BB2) // Top color
    val darkPurple = Color(0xFF663399)  // Bottom color

    val grayFieldColor = Color(0xFFE0E0E0)

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

            // Title txt
            Text(
                text = "Playlist\nRoulette",
                fontSize = 40.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Create Account txt
            Text(
                text = "Create Account:",
                fontSize = 32.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            // Username
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = grayFieldColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black
                )
            )

            // Password
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.Gray) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = grayFieldColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black
                )
            )

            // Create Account button
            Button(
                onClick = {
                    // temp, needs better
                    if (username.isNotBlank() && password.isNotBlank()) {
                        onRegisterSuccess()
                    }
                },
                modifier = Modifier
                    .width(220.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = grayFieldColor
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Create Account",
                    color = Color.Black
                )
            }
        }
    }
}