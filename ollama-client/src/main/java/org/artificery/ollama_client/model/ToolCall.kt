package org.artificery.ollama_client.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ToolCall(
    val function: ToolCallFunction
)

@Serializable
data class ToolCallFunction(
    val name: String,
    val arguments: Map<String, @Contextual Any>
)