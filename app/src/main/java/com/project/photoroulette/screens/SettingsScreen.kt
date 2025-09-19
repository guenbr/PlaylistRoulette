package com.alexhekmat.photoroulette.screens

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexhekmat.photoroulette.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {},
    onSignOutClick: () -> Unit = {},
    settingsViewModel: SettingsViewModel = viewModel(factory
    = SettingsViewModel.Factory(LocalContext.current))
) {
    // Gradient Colors
    val lightPurple = Color(0xFF9A5BB2) // Top color
    val darkPurple = Color(0xFF663399)  // Bottom color
    val grayButtonColor = Color(0xFFE0E0E0)
    val grayFieldColor = Color(0xFFE0E0E0)
    val greenButtonColor = Color(0xFF4AE290) // Success color
    val redColor = Color(0xFFE24444) // Error color
    val warningColor = Color(0xFFFFA726) // Warning color

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Collect the API settings from ViewModel
    val apiUrl by settingsViewModel.apiUrl.collectAsState()
    val apiToken by settingsViewModel.apiToken.collectAsState()
    val connectionStatus by settingsViewModel.connectionStatus.collectAsState()

    // UI state variables
    var tempUrl by remember { mutableStateOf("") }
    var isSaved by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

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
            Spacer(modifier = Modifier.height(64.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 36.dp)
            ) {
                // Back button
                IconButton(
                    onClick = { onBackClick() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Title txt
                Text(
                    text = "Settings",
                    fontSize = 40.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Connect Spotify button
            Button(
                onClick = {
                    val url = "https://ahek.pythonanywhere.com/login"
                    val intent = CustomTabsIntent.Builder().build().intent
                    intent.data = Uri.parse(url)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = grayButtonColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Connect Spotify",
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // API Status Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                backgroundColor = when (connectionStatus) {
                    is SettingsViewModel.ConnectionStatus.Connected -> greenButtonColor
                    is SettingsViewModel.ConnectionStatus.Error -> redColor
                    SettingsViewModel.ConnectionStatus.NotConnected -> warningColor
                },
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when (connectionStatus) {
                            is SettingsViewModel.ConnectionStatus.Connected -> Icons.Default.Check
                            is SettingsViewModel.ConnectionStatus.Error -> Icons.Default.Error
                            SettingsViewModel.ConnectionStatus.NotConnected -> Icons.Default.Warning
                        },
                        contentDescription = "API Status",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = when (connectionStatus) {
                                is SettingsViewModel.ConnectionStatus.Connected ->
                                    "API Connection: Ready"
                                is SettingsViewModel.ConnectionStatus.Error ->
                                    "API Connection: Error"
                                SettingsViewModel.ConnectionStatus.NotConnected ->
                                    "API Connection: Not Connected"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = when (connectionStatus) {
                                is SettingsViewModel.ConnectionStatus.Connected ->
                                    "Token: ${apiToken.take(5)}..."
                                is SettingsViewModel.ConnectionStatus.Error ->
                                    (connectionStatus as SettingsViewModel.ConnectionStatus.Error).message
                                SettingsViewModel.ConnectionStatus.NotConnected ->
                                    "Please paste a Spotify API URL below"
                            },
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // API URL text input field
            TextField(
                value = tempUrl,
                onValueChange = {
                    tempUrl = it
                    showError = false
                },
                placeholder = { Text("Paste Spotify API URL here...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = if (showError) 8.dp else 16.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = grayFieldColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (settingsViewModel.updateApiUrlAndExtractToken(tempUrl)) {
                            isSaved = true
                            showError = false
                            tempUrl = "" // Clear the field after successful save

                            coroutineScope.launch {
                                delay(1000)
                                isSaved = false
                            }
                        } else {
                            showError = true
                            errorMessage = "Invalid URL - must include a token"
                        }
                    }
                ),
                isError = showError
            )

            // Error message
            if (showError) {
                Text(
                    text = errorMessage,
                    color = redColor,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 8.dp, bottom = 16.dp)
                )
            }

            // button to submit the URL
            Button(
                onClick = {
                    if (settingsViewModel.updateApiUrlAndExtractToken(tempUrl)) {
                        isSaved = true
                        showError = false
                        tempUrl = "" // Clear the field after successful save

                        coroutineScope.launch {
                            delay(1000)
                            isSaved = false
                        }
                    } else {
                        showError = true
                        errorMessage = "Invalid URL - must include a token"
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp, bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (isSaved) greenButtonColor else grayButtonColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (isSaved) "Saved!" else "Save URL",
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }

            if (connectionStatus !is SettingsViewModel.ConnectionStatus.Connected) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    backgroundColor = Color(0xFF512DA8),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "How to connect:",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "1. Click 'Connect Spotify' above to sign in\n" +
                                    "2. After logging in, copy the full URL\n" +
                                    "3. Paste the URL in the field above\n" +
                                    "4. Click 'Save URL'",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Sign Out button
            Button(
                onClick = { onSignOutClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = grayButtonColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Sign Out",
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}