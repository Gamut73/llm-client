package org.artificery.ollama_client.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    @SerialName("model") val model: String,
    @SerialName("messages") val messages: List<Message>,
    @SerialName("tools") val tools: List<Tool>? = null,
    @SerialName("format") val format: String = "json",
    @SerialName("stream") val stream: Boolean = false,
    @SerialName("keep_alive") val keepAlive: String? = "5m",
    @SerialName("options") val options: RequestOptions? = null
)

@Serializable
data class Message(
    @SerialName("role") val role: String,
    @SerialName("content") val content: String,
    @SerialName("images") val images: List<ByteArray>? = null,
    @SerialName("tool_calls") val toolCalls: List<ToolCall>? = null
)