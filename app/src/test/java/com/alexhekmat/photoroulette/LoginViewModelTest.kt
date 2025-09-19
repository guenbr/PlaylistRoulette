package com.alexhekmat.photoroulette

import com.alexhekmat.photoroulette.viewmodel.LoginViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel()
    }

    @Test
    fun updateUsername_updatesState() {
        viewModel.updateUsername("testUser")
        assertEquals("testUser", viewModel.uiState.value.username)
    }

    @Test
    fun updatePassword_updatesState() {
        viewModel.updatePassword("testPass")
        assertEquals("testPass", viewModel.uiState.value.password)
    }

    @Test
    fun login_returnsTrue_forAdminCredentials() {
        viewModel.updateUsername("admin")
        viewModel.updatePassword("admin")
        val result = viewModel.login()
        assertTrue(result)
        assertEquals("", viewModel.uiState.value.username)
    }

    @Test
    fun login_returnsFalse_forWrongCredentials() {
        viewModel.updateUsername("x")
        viewModel.updatePassword("y")
        val result = viewModel.login()
        assertFalse(result)
        assertTrue(viewModel.uiState.value.showError)
    }
}