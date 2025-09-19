package com.alexhekmat.photoroulette.navigation

enum class Screen {
    LOGIN,
    CREATE_ACCOUNT,
    MAIN_MENU,
    SETTINGS,
    GAME_SETUP,
    VIEW_PLAYLISTS,
    GAME,
}

/**
 * A sealed class to protect navigation from the user within the AppNavHost.
 */
sealed class NavigationItem(val route: String) {
    object Login : NavigationItem(Screen.LOGIN.name.lowercase())
    object CreateAccount : NavigationItem(Screen.CREATE_ACCOUNT.name.lowercase())
    object MainMenu : NavigationItem(Screen.MAIN_MENU.name.lowercase())
    object Settings : NavigationItem(Screen.SETTINGS.name.lowercase())
    object GameSetup : NavigationItem(Screen.GAME_SETUP.name.lowercase())
    object ViewPlaylists : NavigationItem(Screen.VIEW_PLAYLISTS.name.lowercase())
    object Game : NavigationItem(Screen.GAME.name.lowercase())
}