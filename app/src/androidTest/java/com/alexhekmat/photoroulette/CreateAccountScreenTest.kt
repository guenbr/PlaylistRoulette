package com.alexhekmat.photoroulette

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexhekmat.photoroulette.screens.RegisterScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateAccountScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                RegisterScreen()
            }
        }
    }

    @Test
    fun createAccountScreen_titleIsDisplayed() {
        composeTestRule.onNodeWithText("Playlist\nRoulette").assertIsDisplayed()
    }

    @Test
    fun createAccountScreen_headerIsDisplayed() {
        composeTestRule.onNodeWithText("Create Account:").assertIsDisplayed()
    }

    @Test
    fun createAccountScreen_usernameFieldIsDisplayed() {
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
    }

    @Test
    fun createAccountScreen_passwordFieldIsDisplayed() {
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
    }

    @Test
    fun createAccountScreen_createAccountButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
    }

    @Test
    fun createAccountScreen_enteringText_updatesInputFields() {
        composeTestRule.onNodeWithText("Username").performTextInput("newuser")
        composeTestRule.onNodeWithText("Password").performTextInput("newpass")

        composeTestRule.onNode(hasText("newuser", substring = true)).assertExists()
    }
}