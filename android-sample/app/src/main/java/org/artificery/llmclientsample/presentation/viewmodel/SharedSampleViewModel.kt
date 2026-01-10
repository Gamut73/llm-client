package org.artificery.llmclientsample.presentation.viewmodel

import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.artificery.llm_client.LLMClient
import org.artificery.llm_client.impl.GeminiModel
import org.artificery.llm_client.model.AudioTranscriptionPrompt
import org.artificery.llm_client.model.ImageFromBytes
import org.artificery.llm_client.model.ImageFromUrl
import org.artificery.llm_client.model.ImageResponse
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
import org.artificery.llm_client.model.enums.AudioMimeType
import org.artificery.llm_client.model.enums.ImageMimeType
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class SharedSampleViewModel @Inject constructor(
    private val llmClient: LLMClient
): ViewModel() {

    suspend fun textPrompt(prompt: TextPrompt): String {
        val response = withContext(Dispatchers.IO) {
            llmClient.getTextResponseFromTextPrompt(prompt)
        }
        return when (response) {
            is TextResponse.Success -> response.text
            is TextResponse.Error -> "Error: ${response.message}"
        }
    }

    suspend fun textWithImagesPrompt(
        prompt: TextPrompt,
        imageUrls: List<String>,
        imagesURIs: List<Uri>,
        contentResolver: ContentResolver,
    ): String {
        val (imagesFromUrls, imagesBytes) = getImageBytes(imageUrls, imagesURIs, contentResolver)
        val response = withContext(Dispatchers.IO) {
            llmClient.getTextResponseFromTextWithImagesPrompt(
                org.artificery.llm_client.model.TextWithImagesPrompt(
                    text = prompt.text,
                    imagesFromUrls = imagesFromUrls,
                    imagesBytes = imagesBytes
                )
            )
        }
        return when (response) {
            is TextResponse.Success -> response.text
            is TextResponse.Error -> "Error: ${response.message}"
        }
    }

    suspend fun textWithImagesPromptForImageResponse(
        prompt: TextPrompt,
        imageUrls: List<String>,
        imagesURIs: List<Uri>,
        contentResolver: ContentResolver,
    ): ImageResponse {
        val (imagesFromUrls, imagesBytes) = getImageBytes(imageUrls, imagesURIs, contentResolver)
        val response = withContext(Dispatchers.IO) {
            llmClient.getImageResponseFromTextWithImagesPrompt(
                org.artificery.llm_client.model.TextWithImagesPrompt(
                    text = prompt.text,
                    imagesFromUrls = imagesFromUrls,
                    imagesBytes = imagesBytes,
                    model = GeminiModel.GEMINI_2_5_FLASH_IMAGE.defaultModel
                )
            )
        }
        return response
    }

    private suspend fun getImageBytes(
        imageUrls: List<String>,
        imagesURIs: List<Uri>,
        contentResolver: ContentResolver
    ): Pair<List<ImageFromUrl>, List<ImageFromBytes>> {
        val imagesFromUrls = imageUrls.map { url ->
            val mimeType = getMimeTypeFromUrl(url)
            ImageFromUrl(
                imageUrl = url,
                mimeType = mapMimeTypeToImageMimeType(mimeType)
            )
        }
        val imagesBytes = imagesURIs.map { imageUri ->
            ImageFromBytes(
                imageBytes = getBytesFromUri(contentResolver, imageUri),
                mimeType =
                    mapMimeTypeToImageMimeType(contentResolver.getType(imageUri))
            )
        }
        return Pair(imagesFromUrls, imagesBytes)
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    suspend fun transcribeAudioToText(audioFileUri: Uri, contentResolver: ContentResolver): String {
        val response = withContext(Dispatchers.IO) {
            val audioBytes = getBytesFromUri(contentResolver, audioFileUri)

            llmClient.transcribeAudioToText(
                AudioTranscriptionPrompt(
                    audioBytes = audioBytes,
                    audioMimeType = mapMimeTypeToAudioMimeType(
                        contentResolver.getType(audioFileUri))
                )
            )
        }
        return when (response) {
            is TextResponse.Success -> response.text
            is TextResponse.Error -> "Transcription Error: ${response.message}"
        }
    }

    private fun getBytesFromUri(
        contentResolver: ContentResolver,
        fileUri: Uri
    ): ByteArray = contentResolver.openInputStream(fileUri)?.use { inputStream ->
        inputStream.readBytes()
    } ?: throw IllegalArgumentException("Could not open file")

    private fun mapMimeTypeToAudioMimeType(mimeType: String?): AudioMimeType {
        return when (mimeType) {
            "audio/wav", "audio/x-wav" -> AudioMimeType.WAV
            "audio/mp3", "audio/mpeg" -> AudioMimeType.MP3
            "audio/flac" -> AudioMimeType.FLAC
            "audio/ogg" -> AudioMimeType.OGG
            "audio/aac" -> AudioMimeType.AAC
            "audio/aiff", "audio/x-aiff" -> AudioMimeType.AIFF
            else -> throw IllegalArgumentException("Unsupported audio mime type: $mimeType")
        }
    }

    private fun mapMimeTypeToImageMimeType(mimeType: String?): ImageMimeType {
        return when (mimeType) {
            "image/png" -> ImageMimeType.PNG
            "image/jpeg", "image/jpg" -> ImageMimeType.JPEG
            "image/webp" -> ImageMimeType.WEBP
            else -> throw IllegalArgumentException("Unsupported image mime type: $mimeType")
        }
    }

    suspend fun getMimeTypeFromUrl(urlString: String): String {
        val extension = MimeTypeMap.getFileExtensionFromUrl(urlString)
        if (extension.isNotEmpty()) {
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)?.let {
                return it
            }
        }

        return withContext(Dispatchers.IO) {
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "HEAD"
                connection.connect()
                connection.contentType ?: "image/*"
            } catch (e: Exception) {
                Log.e("SharedSampleViewModel", "Failed to get mime type from URL: $urlString", e)
                "image/*"
            }
        }
    }

}