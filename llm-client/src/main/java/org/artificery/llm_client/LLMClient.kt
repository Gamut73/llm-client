package org.artificery.llm_client

import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse

interface LLMClient {
    fun getTextResponseFromTextPrompt(prompt: TextPrompt): TextResponse

    fun transcribeAudioToText(audioBytes: ByteArray, audioMimeType: AudioMimeType): TextResponse
}

enum class AudioMimeType(val mimeType: String) {
    WAV("audio/wav"),
    MP3("audio/mp3"),
    FLAC("audio/flac"),
    OGG("audio/ogg"),
    AAC("audio/aac"),
    AIFF("audio/aiff"),
}