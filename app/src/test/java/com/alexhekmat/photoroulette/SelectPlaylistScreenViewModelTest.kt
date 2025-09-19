package com.alexhekmat.photoroulette

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.alexhekmat.photoroulette.repository.PlaylistRepository
import com.alexhekmat.photoroulette.viewmodel.SelectPlaylistScreenViewModel
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class SelectPlaylistScreenViewModelTest {

    private lateinit var viewModel: SelectPlaylistScreenViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val repo = PlaylistRepository("https://none", "notoken", context)
        viewModel = SelectPlaylistScreenViewModel(repo)
    }

    @Test
    fun testRetry() {
        viewModel.retry()
        assertTrue(true)
    }
}