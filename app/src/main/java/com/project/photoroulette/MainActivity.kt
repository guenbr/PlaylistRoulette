package com.alexhekmat.photoroulette

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alexhekmat.photoroulette.database.AppDatabaseProvider
import com.alexhekmat.photoroulette.navigation.AppNavHost

/**
 * Main activity for the application
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppDatabaseProvider.getDatabase(applicationContext)
        setContent {
            AppNavHost()
        }
    }
}