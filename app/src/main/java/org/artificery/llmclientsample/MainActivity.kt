package org.artificery.llmclientsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.artificery.llm_client.impl.GeminiLLMClientConfig
import org.artificery.llm_client.impl.GeminiLLMClientImpl
import org.artificery.llm_client.impl.GeminiModel
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
import org.artificery.llmclientsample.ui.theme.LLMClientSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LLMClientSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
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

    Text(
        text = "LLM response: $responseText",
        modifier = modifier
    )
}

