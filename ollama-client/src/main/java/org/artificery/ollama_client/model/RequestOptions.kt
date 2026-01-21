package org.artificery.ollama_client.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestOptions(
    val numKeep: Int? = null,
    val seed: Int? = null,
    val numPredict: Int? = null,
    val topK: Int? = null,
    val topP: Float? = null,
    val minP: Float? = null,
    val tfsZ: Float? = null,
    val typicalP: Float? = null,
    val repeatLastN: Int? = null,
    val temperature: Float? = null,
    val repeatPenalty: Float? = null,
    val presencePenalty: Float? = null,
    val frequencyPenalty: Float? = null,
    val mirostat: Int? = null,
    val mirostatTau: Float? = null,
    val mirostatEta: Float? = null,
    val penalizeNewline: Boolean? = null,
    val stop: List<String>? = null
)