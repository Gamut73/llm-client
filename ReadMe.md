# LLM-Client

A Kotlin library providing a unified interface for various Large Language Model (LLM) providers.

## Overview

LLM-Client is a Kotlin library that offers a consistent API for interacting with different LLM providers. It abstracts away provider-specific implementation details.

## Supported Providers

- **[Gemini API](https://ai.google.dev/gemini-api/docs)**
- **[Ollama](https://ollama.com/)**

## Features

- **Text Prompts** - Generate text responses from simple text prompts
- **Multi-modal Support** - Send text with images (URLs or byte arrays)
- **Audio Transcription** - Transcribe audio files to text
- **Type-safe API** - Kotlin-first design with sealed classes for responses
- **Provider Agnostic** - Easy to switch between different LLM providers

## Installation

### Gradle (Kotlin DSL)

Add the dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.gamut73:llm_client:1.1.1")
}
```

### Maven

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.gamut73</groupId>
    <artifactId>llm_client</artifactId>
    <version>1.1.1</version>
</dependency>
```

## Usage

### 1. Initialize the Client

Currently, only Gemini, and Ollama have implementations. Create a client instance:

##### Gemini
```kotlin
val config = GeminiLLMClientConfig(
    apiKey = "YOUR_GEMINI_API_KEY",
    defaultModel = "<model_name>" // e.g. "gemini-2.5-flash"
)

val llmClient: LLMClient = GeminiLLMClientImpl(config)
```

##### Ollama
```kotlin
val config = OllamaLLMClientConfig(
    baseUrl = "http://localhost:11434",
    defaultModel = "<model_name>" // whatever model you have downloaded, e.g. "llama2"
)
val llmClient: LLMClient = OllamaLLMClientImpl(config)
```

## Requirements

- Kotlin 1.9+
- Java 11+
- Android (optional): API level 21+

## Sample Project

Check out the `android-sample` module in this repository for a complete Android app demonstrating all features of the library.

