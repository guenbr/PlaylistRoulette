package com.alexhekmat.photoroulette

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexhekmat.photoroulette.screens.MainMenuScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainMenuScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MaterialTheme {
                MainMenuScreen()
            }
        }
    }

    @Test
    fun mainMenuScreen_titleIsDisplayed() {
        composeTestRule.onNodeWithText("Playlist\nRoulette").assertIsDisplayed()
    }

    @Test
    fun mainMenuScreen_createGameButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Create Game").assertIsDisplayed()
    }

    @Test
    fun mainMenuScreen_settingsButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
    }
}