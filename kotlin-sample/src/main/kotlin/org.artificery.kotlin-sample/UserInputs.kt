package org.artificery.`kotlin-sample`

enum class PromptType {
    TEXT_TO_TEXT,
    TEXT_TO_IMAGE,
    TEXT_AND_IMAGES_TO_TEXT,
    TEXT_AND_IMAGES_TO_IMAGE
}

fun userInputForPromptType(): PromptType {
    println("Select prompt type:")
    println("1. Text to Text")
    println("2. Text to Image")
    println("3. Text and Images to Text")
    println("4. Text and Images to Image")
    print("Enter your choice (1-4): ")

    while (true) {
        val input = readlnOrNull()?.toIntOrNull()
        when (input) {
            1 -> return PromptType.TEXT_TO_TEXT
            2 -> return PromptType.TEXT_TO_IMAGE
            3 -> return PromptType.TEXT_AND_IMAGES_TO_TEXT
            4 -> return PromptType.TEXT_AND_IMAGES_TO_IMAGE
            else -> {
                print("Invalid choice. Please enter a number between 1 and 4: ")
            }
        }
    }
}

fun userInputForModel(): String {
    print("Enter the model to use: ")
    while (true) {
        val input = readlnOrNull()?.trim()
        if (!input.isNullOrEmpty()) {
            return input
        }
        print("Model cannot be empty. Please enter a model: ")
    }
}

fun userInputForPrompt(): String {
    print("Enter your prompt: ")
    while (true) {
        val input = readlnOrNull()?.trim()
        if (!input.isNullOrEmpty()) {
            return input
        }
        print("Prompt cannot be empty. Please enter a prompt: ")
    }
}

fun userInputForImageUrl(): String {
    print("Enter image URL: ")
    return readlnOrNull()?.trim() ?: ""
}
