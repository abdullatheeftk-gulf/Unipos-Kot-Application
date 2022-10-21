package com.gulfappdeveloper.project3.di

import com.gulfappdeveloper.project3.data.remote.ApiServiceImpl
import com.gulfappdeveloper.project3.domain.services.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.cache.*
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
            install(HttpCache)

            engine {
                connectTimeout = 30_000
                socketTimeout = 30_000
            }
        }
    }


    @Provides
    @Singleton
    fun provideApiService(client: HttpClient): ApiService {
        return ApiServiceImpl(client)
    }
}