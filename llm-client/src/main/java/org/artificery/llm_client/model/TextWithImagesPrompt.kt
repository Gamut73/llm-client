package org.artificery.llm_client.model

import org.artificery.llm_client.model.enums.ImageMimeType

data class TextWithImagesPrompt(
    val text: String,
    val imagesFromUrls: List<ImageFromUrl>,
    val imagesBytes: List<ImageFromBytes> = emptyList(),
    val model: String? = null,
)

data class ImageFromUrl(
    val imageUrl: String,
    val mimeType: ImageMimeType
)

data class ImageFromBytes(
    val imageBytes: ByteArray,
    val mimeType: ImageMimeType
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageFromBytes

        if (!imageBytes.contentEquals(other.imageBytes)) return false
        if (mimeType != other.mimeType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imageBytes.contentHashCode()
        result = 31 * result + mimeType.hashCode()
        return result
    }
}
