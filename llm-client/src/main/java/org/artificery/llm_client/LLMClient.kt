package org.artificery.llm_client

import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
import java.net.URI

interface LLMClient {
    fun getTextResponseFromTextPrompt(prompt: TextPrompt): TextResponse

    fun transcribeAudioToText(audioFileUri: URI): TextResponse
}