package org.artificery.llmclientsample.presentation.screen

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.artificery.llm_client.impl.GeminiLLMClientConfig
import org.artificery.llm_client.impl.GeminiLLMClientImpl
import org.artificery.llm_client.impl.GeminiModel
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
import org.artificery.llmclientsample.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextPromptScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var responseText by remember { mutableStateOf("Loading...") }

    LaunchedEffect(Unit) {
        val geminiLLMClientConfig = GeminiLLMClientConfig(
            model = GeminiModel.GEMINI_2_5_FLASH,
            apiKey = BuildConfig.GEMINI_API_KEY
        )
        val llmClient = GeminiLLMClientImpl(geminiLLMClientConfig)
        val textPrompt = TextPrompt(text = "Write an Acrostic poem about programming using the word 'Code'")

        responseText = withContext(Dispatchers.IO) {
            when (val response = llmClient.getTextResponseFromTextPrompt(textPrompt)) {
                is TextResponse.Success -> response.text
                is TextResponse.Error -> "Error: ${response.message}"
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Text Prompt Example") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            backDispatcher?.onBackPressed()
                        }
                    ){
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Text(
            text = "LLM response: $responseText",
            modifier = Modifier.padding(innerPadding)
        )
    }
}