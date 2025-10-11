package org.artificery.llmclientsample.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.artificery.llm_client.LLMClient
import org.artificery.llm_client.model.TextPrompt
import javax.inject.Inject

@HiltViewModel
class SharedSampleViewModel @Inject constructor(
    private val llmClient: LLMClient
): ViewModel() {

    suspend fun textPrompt(prompt: TextPrompt): String {
        val response = withContext(Dispatchers.IO) {
            llmClient.getTextResponseFromTextPrompt(prompt)
        }
        return when (response) {
            is org.artificery.llm_client.model.TextResponse.Success -> response.text
            is org.artificery.llm_client.model.TextResponse.Error -> "Error: ${response.message}"
        }
    }
}