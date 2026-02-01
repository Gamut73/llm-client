package org.artificery.llm_client.impl

import kotlinx.coroutines.runBlocking
import org.artificery.llm_client.LLMClient
import org.artificery.llm_client.model.AudioTranscriptionPrompt
import org.artificery.llm_client.model.ImageFromBytes
import org.artificery.llm_client.model.ImageFromUrl
import org.artificery.llm_client.model.ImageResponse
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
import org.artificery.llm_client.model.TextWithImagesPrompt
import org.artificery.ollama_client.OllamaClient
import org.artificery.ollama_client.OllamaClientImpl
import org.artificery.ollama_client.model.GenerateRequest
import org.artificery.ollama_client.model.OllamaClientConfig
import java.net.URI
import java.util.Base64

class OllamaLLMClientImpl(
    private val defaultModel: String,
    private val client: OllamaClient = OllamaClientImpl(
        config = OllamaClientConfig()
    )
) : LLMClient {

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
        val generateRequest = GenerateRequest(
            model = prompt.model ?: defaultModel,
            prompt = prompt.text,
            stream = false,
            images = getImagesBase64Strings(
                prompt.imagesFromUrls,
                prompt.imagesBytes
            )
        )

        val response = runBlocking {
            client.generate(generateRequest)
        }

        return TextResponse.Success(response.response)
    }

    override fun transcribeAudioToText(prompt: AudioTranscriptionPrompt): TextResponse {
        TODO("Not yet implemented")
    }

    override fun getImageResponseFromTextWithImagesPrompt(prompt: TextWithImagesPrompt): ImageResponse {
        val generateRequest = GenerateRequest(
            model = prompt.model ?: defaultModel,
            prompt = prompt.text,
            stream = false,
            images = getImagesBase64Strings(
                prompt.imagesFromUrls,
                prompt.imagesBytes
            )
        )

        val response = runBlocking {
            client.generate(generateRequest)
        }

        if (response.image == null) {
            return ImageResponse.Error("No images in response")
        }
        return ImageResponse.Success(
            listOf(decodeImageByteArrayFromBase64String(response.image!!))
        )
    }

    private fun getImagesBase64Strings(
        imagesUrls: List<ImageFromUrl>,
        imagesFromBytes: List<ImageFromBytes>
    ): List<String> {
        val imagesBytes = mutableListOf<String>()

        imagesFromBytes.forEach { imageFromBytes ->
            imagesBytes.add(Base64.getEncoder().encodeToString(imageFromBytes.imageBytes))
        }

        imagesUrls.forEach { imageFromUrl ->
            val url = URI(imageFromUrl.imageUrl).toURL()
            imagesBytes.add(Base64.getEncoder().encodeToString(url.readBytes()))
        }

        return imagesBytes
    }

    private fun decodeImageByteArrayFromBase64String(base64String: String): ByteArray =
        Base64.getDecoder().decode(base64String)
}