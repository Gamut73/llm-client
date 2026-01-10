package org.artificery.llm_client.model

import org.artificery.llm_client.model.enums.AudioMimeType

data class AudioTranscriptionPrompt(
    val audioBytes: ByteArray,
    val audioMimeType: AudioMimeType,
    val model: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AudioTranscriptionPrompt

        if (!audioBytes.contentEquals(other.audioBytes)) return false
        if (audioMimeType != other.audioMimeType) return false
        if (model != other.model) return false

        return true
    }

    override fun hashCode(): Int {
        var result = audioBytes.contentHashCode()
        result = 31 * result + audioMimeType.hashCode()
        result = 31 * result + (model?.hashCode() ?: 0)
        return result
    }
}
