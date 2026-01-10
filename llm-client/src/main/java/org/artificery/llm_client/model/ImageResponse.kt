package org.artificery.llm_client.model

sealed class ImageResponse {
    data class Error(val message: String) : ImageResponse()

    data class Success(
        val imageByteArrays: List<ByteArray>
    ): ImageResponse()
}