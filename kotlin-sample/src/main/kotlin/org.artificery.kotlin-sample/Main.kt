package org.artificery.kotlin_sample

import org.artificery.`kotlin-sample`.PromptType
import org.artificery.`kotlin-sample`.userInputForImageUrl
import org.artificery.`kotlin-sample`.userInputForModel
import org.artificery.`kotlin-sample`.userInputForPrompt
import org.artificery.`kotlin-sample`.userInputForPromptType
import org.artificery.llm_client.impl.OllamaLLMClientImpl
import org.artificery.llm_client.model.ImageFromUrl
import org.artificery.llm_client.model.ImageResponse
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llm_client.model.TextResponse
import org.artificery.llm_client.model.TextWithImagesPrompt
import org.artificery.llm_client.model.enums.ImageMimeType

fun main() {
    val model = userInputForModel()
    val promptType = userInputForPromptType()

    when (promptType) {
        PromptType.TEXT_TO_TEXT -> {
            val llmClient = OllamaLLMClientImpl(defaultModel = model)
            text2TextPromptExample(llmClient)
        }

        PromptType.TEXT_TO_IMAGE -> {
            val llmClient = OllamaLLMClientImpl(defaultModel = model)
            text2ImagePromptExample(llmClient)
        }

        PromptType.TEXT_AND_IMAGES_TO_TEXT -> {
            val llmClient = OllamaLLMClientImpl(defaultModel = model)
            textAndImages2TextPromptExample(llmClient)
        }

        PromptType.TEXT_AND_IMAGES_TO_IMAGE -> {
            println("Not implemented yet")
        }
    }
}

private fun text2ImagePromptExample(
    llmClient: OllamaLLMClientImpl,
) {
    val prompt = userInputForPrompt()

    val textWithImagesPrompt = TextWithImagesPrompt(
        text = prompt,
    )

    llmClient.getImageResponseFromTextWithImagesPrompt(textWithImagesPrompt).let { response ->
        when (response) {
            is ImageResponse.Error -> {
                println("Error: ${response.message}")
            }
            is ImageResponse.Success -> {
                val imageByteArray = response.imageByteArrays.get(0)
                java.io.File("generated_image.png").writeBytes(imageByteArray)
            }
        }
    }
}

private fun textAndImages2TextPromptExample(
    llmClient: OllamaLLMClientImpl,
) {
    val prompt = userInputForPrompt()
    val imageUrl = userInputForImageUrl()
    val textWithImagesPrompt = TextWithImagesPrompt(
        text = prompt,
        imagesFromUrls = listOf(
            ImageFromUrl(
                imageUrl = imageUrl,
                mimeType = detirmineImageMimeTypeFromUrl(imageUrl)
            )
        ),
    )

    llmClient.getTextResponseFromTextWithImagesPrompt(textWithImagesPrompt).let { response ->
        when (response) {
            is TextResponse.Success -> {
                println("LLM Response: ${response.text}")
            }

            is TextResponse.Error -> {
                println("Error: ${response.message}")
            }
        }
    }
}

private fun detirmineImageMimeTypeFromUrl(imageUrl: String): ImageMimeType {
    return when {
        imageUrl.endsWith(".png", ignoreCase = true) -> ImageMimeType.PNG
        imageUrl.endsWith(".jpeg", ignoreCase = true) || imageUrl.endsWith(
            ".jpg",
            ignoreCase = true
        ) -> ImageMimeType.JPEG
        else -> ImageMimeType.JPEG
    }
}

private fun text2TextPromptExample(
    llmClient: OllamaLLMClientImpl,
) {
    val prompt = userInputForPrompt()
    val textPrompt = TextPrompt(
        text = prompt
    )

    llmClient.getTextResponseFromTextPrompt(textPrompt).let { response ->
        when (response) {
            is TextResponse.Success -> {
                println("LLM Response: ${response.text}")
            }

            is TextResponse.Error -> {
                println("Error: ${response.message}")
            }
        }
    }
}

