package com.alexhekmat.photoroulette

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexhekmat.photoroulette.screens.LoginScreen
import com.alexhekmat.photoroulette.viewmodel.LoginViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                LoginScreen(
                    onCreateAccountButton = {},
                    onLoginSuccess = {},
                    loginViewModel = LoginViewModel()
                )
            }
        }
    }

    @Test
    fun loginScreen_titleIsDisplayed() {
        composeTestRule.onNodeWithText("Playlist\nRoulette").assertIsDisplayed()
    }

    @Test
    fun loginScreen_usernameFieldIsDisplayed() {
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
    }

    @Test
    fun loginScreen_passwordFieldIsDisplayed() {
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }

    @Test
    fun loginScreen_loginButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }

    @Test
    fun loginScreen_createAccountButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
    }

    @Test
    fun loginScreen_errorMessageIsDisplayedForInvalidCredentials() {
        composeTestRule.onNodeWithText("Username").performTextInput("wrong")
        composeTestRule.onNodeWithText("Password").performTextInput("wrong")

        composeTestRule.onNodeWithText("Login").performClick()
        composeTestRule.onNodeWithText("Invalid username or password").assertIsDisplayed()
    }

    @Test
    fun loginScreen_enteringText_updatesFields() {
        composeTestRule.onNodeWithText("Username").performTextInput("testuser")
        composeTestRule.onNodeWithText("Password").performTextInput("testpass")
        composeTestRule.onNode(hasText("testuser", substring = true)).assertExists()
    }
}