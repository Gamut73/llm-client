package org.artificery.ollama_client.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerateResponse(
    @SerialName("model") val model: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("response") val response: String,
    @SerialName("done") val done: Boolean,
    @SerialName("done_reason") val doneReason: String? = null,
    @SerialName("image") val image: String? = null,
)