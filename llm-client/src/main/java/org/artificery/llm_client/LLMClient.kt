package org.artificery.llm_client

import org.artificery.llm_client.model.AudioMimeType
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse

interface LLMClient {
    fun getTextResponseFromTextPrompt(prompt: TextPrompt): TextResponse

    fun transcribeAudioToText(audioBytes: ByteArray, audioMimeType: AudioMimeType): TextResponse
}