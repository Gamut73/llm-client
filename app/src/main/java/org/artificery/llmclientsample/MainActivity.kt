package org.artificery.llmclientsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.artificery.llmclientsample.presentation.screen.HomeScreen
import org.artificery.llmclientsample.presentation.screen.TextPromptScreen
import org.artificery.llmclientsample.ui.theme.LLMClientSampleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LLMClientSampleTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(navController = navController)
                    }
                    composable("textPrompt") {
                        TextPromptScreen(navController = navController)
                    }
                }
            }
        }
    }
}

