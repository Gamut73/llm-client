package org.artificery.llmclientsample.presentation.viewmodel

import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.artificery.llm_client.AudioMimeType
import org.artificery.llm_client.LLMClient
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
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

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    suspend fun transcribeAudioToText(audioFileUri: Uri, contentResolver: ContentResolver): String {
        val response = withContext(Dispatchers.IO) {
            val audioBytes = contentResolver.openInputStream(audioFileUri)?.use { inputStream ->
                inputStream.readBytes()
            } ?: throw IllegalArgumentException("Could not open audio file")

            llmClient.transcribeAudioToText(
                audioBytes,
                mapMimeTypeToAudioMimeType(contentResolver.getType(audioFileUri))
            )
        }
        return when (response) {
            is TextResponse.Success -> response.text
            is TextResponse.Error -> "Transcription Error: ${response.message}"
        }
    }

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
}