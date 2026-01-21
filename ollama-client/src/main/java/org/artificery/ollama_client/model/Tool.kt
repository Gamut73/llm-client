package org.artificery.ollama_client.model

import kotlinx.serialization.Serializable

@Serializable
data class Tool(
    val type: String,
    val function: ToolFunction
)

@Serializable
data class ToolFunction(
    val name: String,
    val description: String,
    val parameters: ToolParameters
)

@Serializable
data class ToolParameters(
    val type: String,
    val required: List<String>,
    val properties: Map<String, ToolParameterProperty>
)

@Serializable
data class ToolParameterProperty(
    val type: String,
    val description: String,
    val enum: List<String>? = null
)
