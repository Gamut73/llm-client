package org.artificery.llm_client.impl

import com.google.genai.Client
import com.google.genai.types.Content
import com.google.genai.types.Part
import org.artificery.llm_client.LLMClient
import org.artificery.llm_client.model.ImageResponse
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
import org.artificery.llm_client.model.TextWithImagesPrompt
import org.artificery.llm_client.model.enums.AudioMimeType
import org.artificery.llm_client.model.toStringPrompt
import java.net.URI

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

    override fun getTextResponseFromTextWithImagesPrompt(
        prompt: TextWithImagesPrompt
    ): TextResponse {
        val parts = mutableListOf<Part>()
        parts.add(Part.fromText(prompt.text))
        prompt.imagesFromUrls.forEach { imageFromUrl ->
            val url = URI(imageFromUrl.imageUrl).toURL()
            parts.add(
                Part.fromBytes(url.readBytes(), imageFromUrl.mimeType.mimeType)
            )
        }
        prompt.imagesBytes.forEach { imageFromBytes ->
            parts.add(
                Part.fromBytes(imageFromBytes.imageBytes, imageFromBytes.mimeType.mimeType)
            )
        }
        val content = Content.builder()
            .role("user")
            .parts(parts)
            .build()

        val generateContent = geminiClient.models.generateContent(
            config.model.modelName,
            content,
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

    override fun getImageResponseFromTextWithImagesPrompt(
        prompt: TextWithImagesPrompt
    ): ImageResponse {
        val parts = mutableListOf<Part>()
        parts.add(Part.fromText(prompt.text))
        prompt.imagesFromUrls.forEach { imageFromUrl ->
            val url = URI(imageFromUrl.imageUrl).toURL()
            parts.add(
                Part.fromBytes(url.readBytes(), imageFromUrl.mimeType.mimeType)
            )
        }
        prompt.imagesBytes.forEach { imageFromBytes ->
            parts.add(
                Part.fromBytes(imageFromBytes.imageBytes, imageFromBytes.mimeType.mimeType)
            )
        }
        val content = Content.builder()
            .role("user")
            .parts(parts)
            .build()

        val model = prompt.model ?: config.model.modelName
        val generateContentResponse = geminiClient.models.generateContent(
            model,
            content,
            null
        )

        generateContentResponse.parts()?.let { parts ->
            val outputByteArrays = parts.filter { it.inlineData().isPresent }
                .map { part -> part.inlineData().get() }
                .filter { blob -> blob.data().isPresent }
                .map { blob -> blob.data().get() }
                .toList()
            return ImageResponse.Success(
                imageByteArrays = outputByteArrays
            )
        }

        return ImageResponse.Error(
            "Image(s) could not be generated\n ${generateContentResponse.text() ?: "No llm text response"}"
        )
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
    GEMINI_2_5_FLASH_IMAGE("gemini-2.5-flash-image"),
    GEMINI_3_PRO_IMAGE_PREVIEW("gemini-3-pro-image-preview")
}