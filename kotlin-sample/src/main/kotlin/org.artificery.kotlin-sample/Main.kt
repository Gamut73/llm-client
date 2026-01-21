package org.artificery.kotlin_sample

import kotlinx.coroutines.runBlocking
import org.artificery.llm_client.impl.OllamaLLMClientImpl
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse

fun main() {
    runBlocking {
        val llmClient = OllamaLLMClientImpl()

        val prompt = TextPrompt(
            text = "What is 1+1?"
        )

        llmClient.getTextResponseFromTextPrompt(prompt).let { response ->
            when (response) {
                is TextResponse.Success -> {
                    println("LLM Response: ${response.text}")
                }
                is TextResponse.Error -> {
                    println("Error: ${response.message}")
                }
            }
        }
    }
}