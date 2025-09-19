package com.alexhekmat.photoroulette

import com.alexhekmat.photoroulette.viewmodel.SettingsViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SettingsViewModelTest {

    private lateinit var status: SettingsViewModel.ConnectionStatus

    @Test
    fun testConnectionStatusError() {
        status = SettingsViewModel.ConnectionStatus.Error("Test error")
        assertEquals("Test error", (status as SettingsViewModel.ConnectionStatus.Error).message)
    }

    @Test
    fun testConnectionStatusEquals() {
        val error1 = SettingsViewModel.ConnectionStatus.Error("X")
        val error2 = SettingsViewModel.ConnectionStatus.Error("X")
        assertEquals(error1, error2)
    }
}