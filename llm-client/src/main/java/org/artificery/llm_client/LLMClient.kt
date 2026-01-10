package org.artificery.llm_client

import org.artificery.llm_client.model.AudioTranscriptionPrompt
import org.artificery.llm_client.model.ImageResponse
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
import org.artificery.llm_client.model.TextWithImagesPrompt

interface LLMClient {
    fun getTextResponseFromTextPrompt(prompt: TextPrompt): TextResponse

    fun getTextResponseFromTextWithImagesPrompt(prompt: TextWithImagesPrompt): TextResponse

    fun transcribeAudioToText(prompt: AudioTranscriptionPrompt): TextResponse

    fun getImageResponseFromTextWithImagesPrompt(prompt: TextWithImagesPrompt): ImageResponse
}