package org.artificery.ollama_client.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerateRequest(
    @SerialName("model") val model: String,
    @SerialName("stream") val stream: Boolean,
    @SerialName("prompt") val prompt: String? = null,
    @SerialName("suffix") val suffix: String? = null,
    @SerialName("format") val format: String? = null,
    @SerialName("options") val options: RequestOptions? = null,
    @SerialName("system") val system: String? = null,
    @SerialName("template") val template: String? = null,
    @SerialName("raw") val raw: Boolean? = null,
    @SerialName("keep_alive") val keepAlive: Int? = null,
    @SerialName("images") val images: List<String>? = null,
)
