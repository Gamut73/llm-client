package org.artificery.ollama_client.model

data class OllamaClientConfig(
    val protocol: String = "http",
    val host: String = "localhost",
    val port: Int = 11434,
    val defaultModel: String = "tinyllama",
    val requestTimeout: Long = 300_000L
)

fun OllamaClientConfig.getBaseUrl(): String {
    return "$protocol://$host:$port/api"
}