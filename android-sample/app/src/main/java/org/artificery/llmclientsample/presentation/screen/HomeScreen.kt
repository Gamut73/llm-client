package org.artificery.llmclientsample.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.artificery.llmclientsample.MainActivity

@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Button(
                onClick = {
                    navController.navigate(MainActivity.Companion.NavRoutes.TEXT_PROMPT)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Go to Text Prompt Screen")
            }
            Button(
                onClick = {
                    navController.navigate(MainActivity.Companion.NavRoutes.TRANSCRIPTION)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Go to Transcription Screen")
            }
            Button(
                onClick = {
                    navController.navigate(
                        MainActivity.Companion.NavRoutes.TEXT_WITH_IMAGES_PROMPT_SAMPLE
                    )
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Go to TextWithImagesPrompt Screen")
            }
        }

    }
}