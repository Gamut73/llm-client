package org.artificery.llmclientsample.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.artificery.llm_client.LLMClient
import javax.inject.Inject

@HiltViewModel
class TextPromptViewModel @Inject constructor(
    private val llmClient: LLMClient
): ViewModel() {
}