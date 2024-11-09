package com.sweetmesoft.kmplibrary.tools

import com.sweetmesoft.kmplibrary.controls.alerts.PopupHandler
import com.sweetmesoft.kmplibrary.objects.ErrorResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.ByteReadChannel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

expect fun createHttpClient(): HttpClient

object NetworkUtils {
    val httpClient = createHttpClient()

    suspend inline fun <reified T> get(url: String, showLoading: Boolean = true): Result<T> {
        if (showLoading) {
            PopupHandler.showLoading()
        }
        val response = httpClient.get(url) {
            contentType(ContentType.Application.Json)
            headers.append(
                "CurrentDate",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
            )
            headers.append("Language", getCurrentLanguage())
        }

        if (showLoading) {
            PopupHandler.hideLoading()
        }

        if (response.status.value != 200) {
            val error = response.body<ErrorResponse>()
            println("HTTP Error: $error")
            PopupHandler.displayAlert(response.status.description, error.title)
            return Result.failure(Exception(error.detail))
        }

        return Result.success(response.body<T>())
    }

    suspend inline fun <reified T> post(
        url: String,
        body: Any? = null,
        showLoading: Boolean = true
    ): Result<T> {
        if (showLoading) {
            PopupHandler.showLoading()
        }
        val response = httpClient.post(url) {
            contentType(ContentType.Application.Json)
            headers.append(
                "CurrentDate",
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
            )
            headers.append("Language", getCurrentLanguage())
            setBody(body)
        }

        if (showLoading) {
            PopupHandler.hideLoading()
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
        PopupHandler.displayProgress()
        val response = httpClient.get(url) {
            onDownload { bytesSentTotal, contentLength ->
                if (contentLength != null) {
                    PopupHandler.progressProgress =
                        bytesSentTotal.toFloat() / contentLength.toFloat()
                }
            }
        }.bodyAsChannel()

        PopupHandler.progressShow = false
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