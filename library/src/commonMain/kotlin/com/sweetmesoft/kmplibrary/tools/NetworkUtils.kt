package com.sweetmesoft.kmplibrary.tools

import com.sweetmesoft.kmplibrary.controls.alerts.PopupHandler
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.onDownload
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import io.ktor.utils.io.ByteReadChannel
import com.sweetmesoft.kmplibrary.objects.ErrorResponse

object NetworkUtils {
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 60000
            connectTimeoutMillis = 30000
            socketTimeoutMillis = 30000
        }
    }

    suspend inline fun <reified T> get(url: String, showLoading: Boolean = true): Result<T> {
        if (showLoading) {
            PopupHandler.isLoading = true
        }

        val response = httpClient.get(url) {
            contentType(ContentType.Application.Json)
            headers.append(
                "CurrentDate",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
            )
            headers.append(
                "Language",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
            )
        }

        if (showLoading) {
            PopupHandler.isLoading = false
        }

        if (response.status.value != 200) {
            val error = response.body<ErrorResponse>()
            println("HTTP Error: $error")
            PopupHandler.displayAlert(response.status.description, error.title)
            return Result.failure(Exception(error.detail))
        }

        return Result.success(response.body<T>())
    }

    suspend inline fun <reified T> post(url: String, body: Any? = null, showLoading: Boolean = true): Result<T> {
        if (showLoading) {
            PopupHandler.isLoading = true
        }

        val response = httpClient.post(url) {
            contentType(ContentType.Application.Json)
            headers.append(
                "CurrentDate",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
            )
            headers.append(
                "Language",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
            )
            headers.append("Language", getCurrentLanguage())
            setBody(body)
        }

        if (showLoading) {
            PopupHandler.isLoading = false
        }

        if (response.status.value != 200) {
            val error = response.body<ErrorResponse>()
            println("HTTP Error: $error")
            PopupHandler.displayAlert(response.status.description, error.title)
            return Result.failure(Exception(error.detail))
        }

        return Result.success(response.body<T>())
    }

    suspend fun downloadFile(url: String): ByteReadChannel {
        val response = httpClient.get(url) {
            onDownload { bytesSentTotal, contentLength ->
                println("Downloaded $bytesSentTotal of $contentLength")
            }
        }.bodyAsChannel()

        return response
    }

    suspend fun downloadJson(url: String): String {
        val response = httpClient.get(url) {
            onDownload { bytesSentTotal, contentLength ->
                println("Downloaded $bytesSentTotal of $contentLength")
            }
        }.bodyAsText()

        return response
    }
}
