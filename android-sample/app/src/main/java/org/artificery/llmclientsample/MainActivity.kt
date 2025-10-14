package org.artificery.llmclientsample

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.artificery.llmclientsample.presentation.screen.HomeScreen
import org.artificery.llmclientsample.presentation.screen.TextPromptScreen
import org.artificery.llmclientsample.presentation.screen.TextWithImagesPromptScreen
import org.artificery.llmclientsample.presentation.screen.TranscriptionScreen
import org.artificery.llmclientsample.ui.theme.LLMClientSampleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LLMClientSampleTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable(NavRoutes.HOME) {
                        HomeScreen(navController = navController)
                    }
                    composable(NavRoutes.TEXT_PROMPT) {
                        TextPromptScreen()
                    }
                    composable(NavRoutes.TRANSCRIPTION) {
                        TranscriptionScreen()
                    }
                    composable(NavRoutes.TEXT_WITH_IMAGES_PROMPT_SAMPLE) {
                        TextWithImagesPromptScreen()
                    }
                }
            }
        }
    }

    companion object {
        object NavRoutes {
            const val HOME = "home"
            const val TEXT_PROMPT = "textPrompt"
            const val TRANSCRIPTION = "transcription"
            const val TEXT_WITH_IMAGES_PROMPT_SAMPLE = "textWithImagesPromptSample"
        }
    }
}

