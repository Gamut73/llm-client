package org.artificery.llm_client.impl

import org.artificery.llm_client.LLMClient
import org.artificery.llm_client.model.AudioTranscriptionPrompt
import org.artificery.llm_client.model.ImageResponse
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
import org.artificery.llm_client.model.TextWithImagesPrompt

class OllamaLLMClientImpl : LLMClient {
    override fun getTextResponseFromTextPrompt(prompt: TextPrompt): TextResponse {
        TODO("Not yet implemented")
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