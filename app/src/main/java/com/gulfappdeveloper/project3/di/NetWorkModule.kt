package com.gulfappdeveloper.project3.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient(Android){
            install(ContentNegotiation){
                json(
                    contentType = ContentType.Application.Json,
                    json = Json {
                        encodeDefaults = true
                    }
                )
            }
            install(Logging){
                level = LogLevel.ALL
            }

            engine {
                connectTimeout = 30_000
                socketTimeout = 30_000
            }
        }
    }
}