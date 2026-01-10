package org.artificery.llm_client.model

data class TextPrompt(
    val text: String,
    val examples: List<Example> = emptyList(),
    val model: String? = null,
)

fun TextPrompt.toStringPrompt(): String = if (examples.isEmpty()) {
    text
} else {
    val examplesText = examples.joinToString("\n\n") { "Input: ${it.input}\nOutput: ${it.output}" }
    "$text\nUse the example(s): \n$examplesText"
}