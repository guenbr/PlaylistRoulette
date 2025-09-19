package com.alexhekmat.photoroulette.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alexhekmat.photoroulette.screens.*

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationItem.Login.route
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavigationItem.Login.route) {
            LoginScreen(
                onCreateAccountButton = {
                    navController.navigate(NavigationItem.CreateAccount.route)
                },
                onLoginSuccess = {
                    navController.navigate(NavigationItem.MainMenu.route) {
                        popUpTo(NavigationItem.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(NavigationItem.CreateAccount.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(NavigationItem.MainMenu.route) {
                        popUpTo(NavigationItem.CreateAccount.route) { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavigationItem.MainMenu.route) {
            MainMenuScreen(
                onStartGameClick = {
                    navController.navigate(NavigationItem.GameSetup.route)
                },
                onSettingsClick = {
                    navController.navigate(NavigationItem.Settings.route)
                }
            )
        }

        composable(NavigationItem.GameSetup.route) {
            GameSetupScreen(
                onViewPlaylistsClick = {
                    navController.navigate(NavigationItem.ViewPlaylists.route)
                },
                onStartGameClick = { rounds ->
                    navController.navigate("${NavigationItem.Game.route}/$rounds")
                },
                onBackClick = {
                    navController.navigate(NavigationItem.MainMenu.route)
                }
            )
        }

        composable(NavigationItem.ViewPlaylists.route) {
            SelectPlaylistScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavigationItem.Settings.route) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSignOutClick = {
                    navController.navigate(NavigationItem.Login.route) {
                        popUpTo(NavigationItem.MainMenu.route) { inclusive = true }
                    }
                }
            )
        }

        // Game screen navigation
        composable(
            route = "${NavigationItem.Game.route}/{rounds}",
            arguments = listOf(navArgument("rounds") { type = NavType.IntType })
        ) { backStackEntry ->
            val rounds = backStackEntry.arguments?.getInt("rounds") ?: 5

            // Use the GameNavigation composable
            GameNavigation(
                onMainMenuReturn = {
                    navController.navigate(NavigationItem.MainMenu.route) {
                        popUpTo(NavigationItem.MainMenu.route) { inclusive = true }
                    }
                },
                onPlayAgain = {
                    // Navigate back to the GameSetup screen
                    navController.navigate(NavigationItem.GameSetup.route) {
                        popUpTo(NavigationItem.Game.route) { inclusive = true }
                    }
                },
                onNavigateToSettings = {
                    navController.navigate(NavigationItem.Settings.route) {
                        popUpTo(NavigationItem.Game.route) { inclusive = true }
                    }
                },
                roundCount = rounds,

            )
        }
    }
}