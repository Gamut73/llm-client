package org.artificery.ollama_client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.artificery.ollama_client.model.ChatRequest
import org.artificery.ollama_client.model.ChatResponse
import org.artificery.ollama_client.model.GenerateRequest
import org.artificery.ollama_client.model.GenerateResponse
import org.artificery.ollama_client.model.OllamaClientConfig
import org.artificery.ollama_client.model.getBaseUrl

interface OllamaClient {
    suspend fun generate(generateRequest: GenerateRequest): GenerateResponse

    suspend fun chat(chatRequest: ChatRequest): ChatResponse
}

class OllamaClientImpl(
    private val config: OllamaClientConfig,
    private val httpClient: HttpClient = HttpClient(
        CIO.create { requestTimeout = 300_000 }
    )
) : OllamaClient {
    override suspend fun generate(generateRequest: GenerateRequest): GenerateResponse {
        val response = sendRequestWithBody(
            "${config.getBaseUrl()}$GENERATE_ENDPOINT",
            generateRequest
        )

        return processResponse(response)
    }

    override suspend fun chat(chatRequest: ChatRequest): ChatResponse {
        val response = sendRequestWithBody(
            "${config.getBaseUrl()}$CHAT_ENDPOINT",
            chatRequest
        )

        return processResponse(response)
    }

    private suspend inline fun <reified T> sendRequestWithBody(endpoint: String, body: T): HttpResponse =
        httpClient.post(endpoint) {
            contentType(ContentType.Application.Json)

            if (body != null) {
                setBody(body.encodeToString())
            }
        }

    private suspend inline fun <reified T : Any> processResponse(response: HttpResponse): T {
        if (response.status != HttpStatusCode.OK) {
            //TODO: better error handling
            throw Exception("Request failed with status: ${response.status} and body: ${response.body<String>()}")
        }
        return response.body<String>()
            .decodeFromString<T>()
            .getOrThrow()
    }

    companion object {
        const val GENERATE_ENDPOINT = "/generate"
        const val CHAT_ENDPOINT = "/chat"
    }
}

val json = Json {
    ignoreUnknownKeys = true
}

inline fun <reified T : Any> T.encodeToString() : String = json.encodeToString(this)

inline fun <reified T : Any> String.decodeFromString(): Result<T> = runCatching {
    json.decodeFromString(this)
}