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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexhekmat.photoroulette.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onCreateAccountButton: () -> Unit,
    onLoginSuccess: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory())
) {
    // Collect UI state from ViewModel
    val uiState by loginViewModel.uiState.collectAsState()

    // Gradient colors
    val lightPurple = Color(0xFF9A5BB2) // Top color
    val darkPurple = Color(0xFF663399)  // Bottom color

    val grayFieldColor = Color(0xFFE0E0E0)
    val errorColor = Color(0xFFE53935) // Red color for error message

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

            // Login txt
            Text(
                text = "Login:",
                fontSize = 32.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            // Username
            TextField(
                value = uiState.username,
                onValueChange = { loginViewModel.updateUsername(it) },
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
                value = uiState.password,
                onValueChange = { loginViewModel.updatePassword(it) },
                label = { Text("Password", color = Color.Gray) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = if (uiState.showError) 8.dp else 40.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = grayFieldColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black
                )
            )

            // Error message
            if (uiState.showError) {
                Text(
                    text = "Invalid username or password",
                    color = errorColor,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .align(Alignment.Start)
                )
            }

            // Login button
            Button(
                onClick = {
                    if (loginViewModel.login()) {
                        onLoginSuccess()
                    }
                },
                modifier = Modifier
                    .width(180.dp)
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = grayFieldColor
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Login",
                    color = Color.Black
                )
            }

            // Create Account button
            Button(
                onClick = { onCreateAccountButton() },
                modifier = Modifier
                    .width(240.dp)
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = grayFieldColor
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Create Account",
                    color = Color.Black
                )
            }
        }
    }
}