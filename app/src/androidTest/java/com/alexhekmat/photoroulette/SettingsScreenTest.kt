package com.alexhekmat.photoroulette

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexhekmat.photoroulette.screens.SettingsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                SettingsScreen()
            }
        }
    }

    @Test
    fun settingsScreen_titleIsDisplayed() {
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_connectSpotifyButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Connect Spotify").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_apiStatusIsDisplayed() {
        composeTestRule.onNodeWithText("API Connection: Not Connected").assertExists()
    }

    @Test
    fun settingsScreen_urlInputFieldIsDisplayed() {
        composeTestRule.onNodeWithText("Paste Spotify API URL here...").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_saveButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Save URL").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_signOutButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Sign Out").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_enterText_updatesInputField() {
        val testUrl = "https://test.com?token=123"

        composeTestRule.onNodeWithText("Paste Spotify API URL here...")
            .performTextInput(testUrl)

        composeTestRule.onNode(hasText(testUrl, substring = true)).assertExists()
    }
}