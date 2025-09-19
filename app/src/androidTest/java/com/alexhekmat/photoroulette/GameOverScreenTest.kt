package com.alexhekmat.photoroulette

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexhekmat.photoroulette.screens.GameOverScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameOverScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                GameOverScreen(
                    correctAnswers = 3,
                    totalRounds = 5
                )
            }
        }
    }

    @Test
    fun gameOverScreen_headerIsDisplayed() {
        composeTestRule.onNodeWithText("Game Over").assertIsDisplayed()
    }

    @Test
    fun gameOverScreen_scoreIsDisplayed() {
        composeTestRule.onNodeWithText("3/5").assertIsDisplayed()
        composeTestRule.onNodeWithText("Correct Answers").assertIsDisplayed()
    }

    @Test
    fun gameOverScreen_percentageIsDisplayed() {
        composeTestRule.onNodeWithText("60%").assertIsDisplayed()
        composeTestRule.onNodeWithText("Score").assertIsDisplayed()
    }

    @Test
    fun gameOverScreen_performanceMessageIsDisplayed() {
        composeTestRule.onNodeWithText("Good Job!").assertIsDisplayed()
    }

    @Test
    fun gameOverScreen_playAgainButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Play Again").assertIsDisplayed()
    }

    @Test
    fun gameOverScreen_mainMenuButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Main Menu").assertIsDisplayed()
    }
}