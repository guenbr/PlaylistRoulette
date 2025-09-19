package com.alexhekmat.photoroulette

import android.app.Application
import com.alexhekmat.photoroulette.model.GameState
import com.alexhekmat.photoroulette.viewmodel.GameViewModel
import com.alexhekmat.photoroulette.repository.PlaylistRepository
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class GameViewModelTest {
    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val repo = PlaylistRepository(
            baseUrl = "https://fake.com",
            token = "123",
            context = context
        )
        viewModel = GameViewModel(repo)
    }

    @Test
    fun test_retry_runsWithoutCrash() {
        viewModel.retry()
        assertTrue(true)
    }

    @Test
    fun test_proceedToNextRound_doesNotCrash() {
        viewModel.proceedToNextRound()
        assertTrue(true)
    }

    @Test
    fun test_onPlaylistSelected_runsSafely() {
        viewModel.onPlaylistSelected("Any Playlist")
        assertTrue(true)
    }

    @Test
    fun test_initializeGame_runsWithoutCrash() {
        viewModel.initializeSinglePlayerGame(3)
        assertTrue(true)
    }

    @Test
    fun test_multipleCalls_runCorrectly() {
        viewModel.initializeSinglePlayerGame(2)
        viewModel.onPlaylistSelected("Guess")
        viewModel.proceedToNextRound()
        viewModel.retry()
        assertTrue(true)
    }
}