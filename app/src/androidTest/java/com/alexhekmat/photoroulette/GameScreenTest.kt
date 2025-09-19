package com.alexhekmat.photoroulette

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexhekmat.photoroulette.model.GameState
import com.alexhekmat.photoroulette.screens.GameScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun gameScreen_playing_displaysAllElements() {
        val gameState = GameState.Playing(
            currentRound = 1,
            totalRounds = 5,
            songTitle = "Test Song",
            songArtist = "Test Artist",
            albumArtUrl = "https://example.com/image.jpg",
            playlists = listOf("Rock", "Pop", "Hip Hop", "Jazz")
        )

        composeTestRule.setContent {
            MaterialTheme {
                GameScreen(gameState = gameState)
            }
        }

        composeTestRule.onNodeWithText("Round 1 of 5").assertIsDisplayed()

        composeTestRule.onNodeWithText("Test Song").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Artist").assertIsDisplayed()

        composeTestRule.onNodeWithText("Rock").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pop").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hip Hop").assertIsDisplayed()
        composeTestRule.onNodeWithText("Jazz").assertIsDisplayed()
    }

    @Test
    fun gameScreen_error_displaysErrorMessage() {
        val gameState = GameState.Error("Network error")

        composeTestRule.setContent {
            MaterialTheme {
                GameScreen(gameState = gameState)
            }
        }

        // Verify error message is displayed
        composeTestRule.onNodeWithText("Oops! Something went wrong").assertIsDisplayed()
        composeTestRule.onNodeWithText("Network error").assertIsDisplayed()
        composeTestRule.onNodeWithText("Try Again").assertIsDisplayed()
    }

    @Test
    fun gameScreen_selectPlaylist_triggersCallback() {
        val gameState = GameState.Playing(
            currentRound = 1,
            totalRounds = 5,
            songTitle = "Test Song",
            songArtist = "Test Artist",
            albumArtUrl = "https://example.com/image.jpg",
            playlists = listOf("Rock", "Pop", "Hip Hop", "Jazz")
        )

        var selectedPlaylist = ""

        composeTestRule.setContent {
            MaterialTheme {
                GameScreen(
                    gameState = gameState,
                    onPlaylistSelected = { playlist -> selectedPlaylist = playlist }
                )
            }
        }

        composeTestRule.onNodeWithText("Pop").performClick()

        assertEquals("Pop", selectedPlaylist)
    }
}