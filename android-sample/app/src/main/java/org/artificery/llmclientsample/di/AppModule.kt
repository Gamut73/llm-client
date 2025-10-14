package org.artificery.llmclientsample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.artificery.llm_client.LLMClient
import org.artificery.llm_client.impl.GeminiLLMClientConfig
import org.artificery.llm_client.impl.GeminiLLMClientImpl
import org.artificery.llm_client.impl.GeminiModel
import org.artificery.llmclientsample.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideGeminiLLMClientConfig(): GeminiLLMClientConfig = GeminiLLMClientConfig(
        model = GeminiModel.GEMINI_2_5_FLASH,
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    @Provides
    @Singleton
    fun provideLLMClient(): LLMClient = GeminiLLMClientImpl(provideGeminiLLMClientConfig())
}