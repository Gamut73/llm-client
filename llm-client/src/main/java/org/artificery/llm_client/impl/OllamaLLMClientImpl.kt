package org.artificery.llm_client.impl

import kotlinx.coroutines.runBlocking
import org.artificery.llm_client.LLMClient
import org.artificery.llm_client.model.AudioTranscriptionPrompt
import org.artificery.llm_client.model.ImageResponse
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
import org.artificery.llm_client.model.TextWithImagesPrompt
import org.artificery.ollama_client.OllamaClient
import org.artificery.ollama_client.OllamaClientImpl
import org.artificery.ollama_client.model.GenerateRequest
import org.artificery.ollama_client.model.OllamaClientConfig

class OllamaLLMClientImpl(
    private val defaultModel: String,
) : LLMClient {

    private val client: OllamaClient = OllamaClientImpl(
        config = OllamaClientConfig()
    )

    override fun getTextResponseFromTextPrompt(prompt: TextPrompt): TextResponse {
        val generateRequest = GenerateRequest(
            model = prompt.model ?: defaultModel,
            prompt = prompt.text,
            stream = false
        )

        val response = runBlocking {
            client.generate(generateRequest)
        }

        return TextResponse.Success(response.response)
    }

    override fun getTextResponseFromTextWithImagesPrompt(prompt: TextWithImagesPrompt): TextResponse {
        TODO("Not yet implemented")
    }

    override fun transcribeAudioToText(prompt: AudioTranscriptionPrompt): TextResponse {
        TODO("Not yet implemented")
    }

    override fun getImageResponseFromTextWithImagesPrompt(prompt: TextWithImagesPrompt): ImageResponse {
        TODO("Not yet implemented")
    }
}