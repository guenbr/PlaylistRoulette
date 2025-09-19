package com.alexhekmat.photoroulette

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexhekmat.photoroulette.screens.GameSetupScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameSetupScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                GameSetupScreen()
            }
        }
    }

    @Test
    fun gameSetupScreen_titleIsDisplayed() {
        composeTestRule.onNodeWithText("Game Setup").assertIsDisplayed()
    }

    @Test
    fun gameSetupScreen_roundsSelectionTextIsDisplayed() {
        composeTestRule.onNodeWithText("Select Number of Rounds").assertIsDisplayed()
    }

    @Test
    fun gameSetupScreen_roundOptionsAreDisplayed() {
        composeTestRule.onNodeWithText("5").assertIsDisplayed()
        composeTestRule.onNodeWithText("8").assertIsDisplayed()
        composeTestRule.onNodeWithText("10").assertIsDisplayed()
    }

    @Test
    fun gameSetupScreen_viewPlaylistsButtonIsDisplayed() {
        composeTestRule.onNodeWithText("View Playlists").assertIsDisplayed()
    }

    @Test
    fun gameSetupScreen_startGameButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Start Game").assertIsDisplayed()
    }

    @Test
    fun gameSetupScreen_selectRound_highlightsSelection() {
        composeTestRule.onNodeWithText("8").performClick()
    }
}