package org.artificery.llm_client

import org.artificery.llm_client.model.enums.AudioMimeType
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
import org.artificery.llm_client.model.TextWithImagesPrompt

interface LLMClient {
    fun getTextResponseFromTextPrompt(prompt: TextPrompt): TextResponse

    fun getTextResponseFromTextWithImagesPrompt(prompt: TextWithImagesPrompt): TextResponse

    fun transcribeAudioToText(audioBytes: ByteArray, audioMimeType: AudioMimeType): TextResponse
    //TODO: Rethink the MIME type handling which is too specific to Gemini atm...
}