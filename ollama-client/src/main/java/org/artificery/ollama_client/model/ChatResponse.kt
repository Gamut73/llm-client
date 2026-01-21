package org.artificery.ollama_client.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    @SerialName("model") val model: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("message") val message: Message,
    @SerialName("done_reason") val doneReason: String? = null,
    @SerialName("done") val done: Boolean,
    @SerialName("metrics") val metrics: ChatResponseMetrics? = null
)

@Serializable
data class ChatResponseMetrics(
    @SerialName("total_duration") val totalDuration: String? = null,
    @SerialName("load_duration") val loadDuration: String? = null,
    @SerialName("prompt_eval_count") val promptEvalCount: Int? = null,
    @SerialName("prompt_eval_duration") val promptEvalDuration: String? = null,
    @SerialName("eval_count") val evalCount: Int? = null,
    @SerialName("eval_duration") val evalDuration: String? = null
)