package org.artificery.llm_client.impl

import com.google.genai.Client
import com.google.genai.types.Content
import com.google.genai.types.Part
import org.artificery.llm_client.AudioMimeType
import org.artificery.llm_client.LLMClient
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
import org.artificery.llm_client.model.toStringPrompt

class GeminiLLMClientImpl(
    private val config: GeminiLLMClientConfig
) : LLMClient {
    private val geminiClient: Client = Client.builder().apiKey(config.apiKey).build()

    override fun getTextResponseFromTextPrompt(prompt: TextPrompt): TextResponse {
        val generateContent = geminiClient.models.generateContent(
            config.model.modelName,
            prompt.toStringPrompt(),
            null
        )

        return if (generateContent.text() == null) {
            TextResponse.Error("No text in response")
        } else {
            TextResponse.Success(generateContent.text() ?: "")
        }
    }

    override fun transcribeAudioToText(audioBytes: ByteArray, audioMimeType: AudioMimeType): TextResponse {
        val audioPart = Part.fromBytes(audioBytes, audioMimeType.mimeType)
        val textPart = Part.fromText("Transcribe the audio to text.")
        val content = Content.builder()
            .role("user")
            .parts(mutableListOf(textPart, audioPart))
            .build()

        val transcriptionResponse = geminiClient.models.generateContent(
            config.model.modelName,
            content,
            null
        )

        return if (transcriptionResponse.text() == null) {
            TextResponse.Error("No text in response")
        } else {
            TextResponse.Success(transcriptionResponse.text() ?: "")
        }
    }
}

data class GeminiLLMClientConfig(
    val apiKey: String,
    val model: GeminiModel,
)

enum class GeminiModel(val modelName: String) {
    GEMINI_2_5_PRO("gemini-2.5-pro"),
    GEMINI_2_5_FLASH("gemini-2.5-flash"),
    GEMINI_2_5_FLASH_LITE("gemini-2.5-flash-lite"),
    GEMINI_2_0_FLASH("gemini-2.0-flash"),
    GEMINI_2_0_FLASH_LITE("gemini-2.0-flash-lite"),
}