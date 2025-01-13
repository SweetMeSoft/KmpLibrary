package com.sweetmesoft.kmplibrary.tools

import com.sweetmesoft.kmplibrary.controls.alerts.PopupHandler
import com.sweetmesoft.kmplibrary.objects.ErrorResponse
import com.sweetmesoft.kmplibrary.objects.GenericResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.setCookie
import io.ktor.utils.io.ByteReadChannel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

expect fun createHttpClient(timeout: Long = 30000): HttpClient

object NetworkUtils {

    suspend inline fun <reified T> get(
        url: String,
        showLoading: Boolean = true,
        bearer: String = "",
        timeout: Long = 30000
    ): Result<GenericResponse<T>> {
        try {
            if (showLoading) {
                PopupHandler.showLoading()
            }
            val httpClient = createHttpClient(timeout)
            val response = httpClient.get(url) {
                contentType(ContentType.Application.Json)
                headers.append(
                    "CurrentDate",
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                )
                headers.append("Language", getCurrentLanguage())
                if (bearer.isNotEmpty()) {
                    headers.append("Authorization", "Bearer $bearer")
                }
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

            return Result.success(GenericResponse(
                obj = response.body<T>(),
                cookies = response.setCookie().map { it.name + ":" + it.value },
                status = response.status.value,
                headers = response.headers.entries().associate { entry ->
                    entry.key to entry.value
                }
            ))
        } catch (e: HttpRequestTimeoutException) {
            e.printStackTrace()
            return Result.failure(e)
        } finally {
            if (showLoading) {
                PopupHandler.hideLoading()
            }
        }
    }

    suspend inline fun <reified T> post(
        url: String,
        body: Any? = null,
        showLoading: Boolean = true,
        bearer: String = "",
        timeout: Long = 30000
    ): Result<GenericResponse<T>> {
        try {
            if (showLoading) {
                PopupHandler.showLoading()
            }
            val httpClient = createHttpClient(timeout)
            val response = httpClient.post(url) {
                contentType(ContentType.Application.Json)
                headers.append(
                    "CurrentDate",
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                )
                headers.append("Language", getCurrentLanguage())
                if (bearer.isNotEmpty()) {
                    headers.append("Authorization", "Bearer $bearer")
                }
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

            return Result.success(GenericResponse(
                obj = response.body<T>(),
                cookies = response.setCookie().map { it.name + ":" + it.value },
                status = response.status.value,
                headers = response.headers.entries().associate { entry ->
                    entry.key to entry.value
                }
            ))
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        } finally {
            if (showLoading) {
                PopupHandler.hideLoading()
            }
        }
    }

    suspend fun downloadFile(
        url: String,
        timeout: Long = 30000
    ): ByteReadChannel {
        try {
            PopupHandler.displayProgress()
            val httpClient = createHttpClient(timeout)
            val response = httpClient.get(url) {
                onDownload { bytesSentTotal, contentLength ->
                    if (contentLength != null) {
                        PopupHandler.progressProgress =
                            bytesSentTotal.toFloat() / contentLength.toFloat()
                    }
                }
            }.bodyAsChannel()

            return response
        } catch (e: HttpRequestTimeoutException) {
            e.printStackTrace()
            return ByteReadChannel.Empty
        } finally {
            PopupHandler.progressShow = false
        }
    }

    suspend fun downloadJson(
        url: String,
        timeout: Long = 30000
    ): String {
        try {
            val httpClient = createHttpClient(timeout)
            val response = httpClient.get(url) {
                onDownload { bytesSentTotal, contentLength ->
                    println("Downloaded $bytesSentTotal of $contentLength")
                }
            }.bodyAsText()

            return response
        } catch (e: HttpRequestTimeoutException) {
            e.printStackTrace()
            return ""
        }
    }
}