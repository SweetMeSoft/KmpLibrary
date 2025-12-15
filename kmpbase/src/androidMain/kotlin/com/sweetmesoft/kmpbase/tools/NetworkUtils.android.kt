package com.sweetmesoft.kmpbase.tools

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual fun createHttpClient(timeout: Long): HttpClient {
    return HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                explicitNulls = false
            }, contentType = ContentType.Any)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = timeout
            connectTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
    }
}