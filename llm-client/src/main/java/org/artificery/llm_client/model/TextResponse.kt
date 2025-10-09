package org.artificery.llm_client.model

sealed class TextResponse {
    data class Success(val text: String) : TextResponse()
    data class Error(val message: String) : TextResponse()
}