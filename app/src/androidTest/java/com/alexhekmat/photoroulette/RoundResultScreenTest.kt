package com.alexhekmat.photoroulette

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexhekmat.photoroulette.screens.RoundResultScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoundResultScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                RoundResultScreen(
                    currentRound = 2,
                    totalRounds = 5,
                    songTitle = "Test Song",
                    songArtist = "Test Artist",
                    correctPlaylist = "Rock",
                    selectedPlaylist = "Pop"
                )
            }
        }
    }

    @Test
    fun roundResultScreen_roundHeaderIsDisplayed() {
        composeTestRule.onNodeWithText("Round 2 Result").assertIsDisplayed()
    }

    @Test
    fun roundResultScreen_songInfoIsDisplayed() {
        composeTestRule.onNodeWithText("Test Song").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Artist").assertIsDisplayed()
    }

    @Test
    fun roundResultScreen_incorrectAnswer_showsIncorrectMessage() {
        composeTestRule.onNodeWithText("Incorrect!").assertIsDisplayed()
    }

    @Test
    fun roundResultScreen_nextRoundButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Next Round").assertIsDisplayed()
    }
}
