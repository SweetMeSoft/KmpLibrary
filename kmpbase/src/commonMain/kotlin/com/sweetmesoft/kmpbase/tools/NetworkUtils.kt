package com.sweetmesoft.kmpbase.tools

import com.sweetmesoft.kmpbase.controls.alerts.PopupHandler
import com.sweetmesoft.kmpbase.objects.ApiContentType
import com.sweetmesoft.kmpbase.objects.ErrorResponse
import com.sweetmesoft.kmpbase.objects.GenericResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import io.ktor.http.contentType
import io.ktor.http.setCookie
import io.ktor.utils.io.ByteReadChannel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

expect fun createHttpClient(timeout: Long = 30000): HttpClient

object NetworkUtils {
    suspend inline fun <reified T> get(
        url: String,
        showLoading: Boolean = true,
        bearer: String = "",
        timeout: Long = 30000,
        contentType: ApiContentType = ApiContentType.Json
    ): Result<GenericResponse<T>> {
        try {
            if (showLoading) {
                PopupHandler.showLoading()
            }
            val httpClient = createHttpClient(timeout)
            val response = httpClient.get(url) {
                contentType(contentType.contentType)
                headers.append(
                    "CurrentDate",
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                )
                headers.append("Language", getCurrentLanguage())
                if (bearer.isNotEmpty()) {
                    headers.append("Authorization", "Bearer $bearer")
                }
            }

            return handleResponse(showLoading, response)
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
        timeout: Long = 30000,
        contentType: ApiContentType = ApiContentType.Json
    ): Result<GenericResponse<T>> {
        try {
            if (showLoading) {
                PopupHandler.showLoading()
            }
            val httpClient = createHttpClient(timeout)
            val response = httpClient.post(url) {
                contentType(contentType.contentType)
                headers.append(
                    "CurrentDate",
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                )
                headers.append("Language", getCurrentLanguage())
                if (bearer.isNotEmpty()) {
                    headers.append("Authorization", "Bearer $bearer")
                }

                if (contentType == ApiContentType.FormUrlEncoded) {
                    val formParams = when (body) {
                        is io.ktor.http.Parameters -> body
                        is Map<*, *> -> {
                            io.ktor.http.Parameters.build {
                                body.forEach { (key, value) ->
                                    append(key.toString(), value.toString())
                                }
                            }
                        }

                        else -> {
                            throw IllegalArgumentException("Para x-www-form-urlencoded, el body debe ser un Map")
                        }
                    }
                    setBody(io.ktor.client.request.forms.FormDataContent(formParams))
                } else {
                    setBody(body)
                }
            }

            return handleResponse(showLoading, response)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        } finally {
            if (showLoading) {
                PopupHandler.hideLoading()
            }
        }
    }

    suspend inline fun <reified T> put(
        url: String,
        body: Any? = null,
        showLoading: Boolean = true,
        bearer: String = "",
        timeout: Long = 30000,
        contentType: ApiContentType = ApiContentType.Json
    ): Result<GenericResponse<T>> {
        try {
            if (showLoading) {
                PopupHandler.showLoading()
            }
            val httpClient = createHttpClient(timeout)
            val response = httpClient.put(url) {
                contentType(contentType.contentType)
                headers.append(
                    "CurrentDate",
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                )
                headers.append("Language", getCurrentLanguage())
                if (bearer.isNotEmpty()) {
                    headers.append("Authorization", "Bearer $bearer")
                }

                if (contentType == ApiContentType.FormUrlEncoded) {
                    val formParams = when (body) {
                        is io.ktor.http.Parameters -> body
                        is Map<*, *> -> {
                            io.ktor.http.Parameters.build {
                                body.forEach { (key, value) ->
                                    append(key.toString(), value.toString())
                                }
                            }
                        }

                        else -> {
                            throw IllegalArgumentException("Para x-www-form-urlencoded, el body debe ser un Map")
                        }
                    }
                    setBody(io.ktor.client.request.forms.FormDataContent(formParams))
                } else {
                    setBody(body)
                }
            }

            return handleResponse(showLoading, response)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        } finally {
            if (showLoading) {
                PopupHandler.hideLoading()
            }
        }
    }

    suspend inline fun <reified T> patch(
        url: String,
        body: Any? = null,
        showLoading: Boolean = true,
        bearer: String = "",
        timeout: Long = 30000,
        contentType: ApiContentType = ApiContentType.Json
    ): Result<GenericResponse<T>> {
        try {
            if (showLoading) {
                PopupHandler.showLoading()
            }
            val httpClient = createHttpClient(timeout)
            val response = httpClient.patch(url) {
                contentType(contentType.contentType)
                headers.append(
                    "CurrentDate",
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                )
                headers.append("Language", getCurrentLanguage())
                if (bearer.isNotEmpty()) {
                    headers.append("Authorization", "Bearer $bearer")
                }
                if (contentType == ApiContentType.FormUrlEncoded) {
                    val formParams = when (body) {
                        is io.ktor.http.Parameters -> body
                        is Map<*, *> -> {
                            io.ktor.http.Parameters.build {
                                body.forEach { (key, value) ->
                                    append(key.toString(), value.toString())
                                }
                            }
                        }

                        else -> {
                            throw IllegalArgumentException("Para x-www-form-urlencoded, el body debe ser un Map")
                        }
                    }
                    setBody(io.ktor.client.request.forms.FormDataContent(formParams))
                } else {
                    setBody(body)
                }
            }

            return handleResponse(showLoading, response)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        } finally {
            if (showLoading) {
                PopupHandler.hideLoading()
            }
        }
    }

    suspend inline fun <reified T> delete(
        url: String,
        showLoading: Boolean = true,
        bearer: String = "",
        timeout: Long = 30000,
        contentType: ApiContentType = ApiContentType.Json
    ): Result<GenericResponse<T>> {
        try {
            if (showLoading) {
                PopupHandler.showLoading()
            }
            val httpClient = createHttpClient(timeout)
            val response = httpClient.delete(url) {
                contentType(contentType.contentType)
                headers.append(
                    "CurrentDate",
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                )
                headers.append("Language", getCurrentLanguage())
                if (bearer.isNotEmpty()) {
                    headers.append("Authorization", "Bearer $bearer")
                }
            }

            return handleResponse(showLoading, response)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        } finally {
            if (showLoading) {
                PopupHandler.hideLoading()
            }
        }
    }

    suspend fun downloadFile(url: String, timeout: Long = 30000): ByteReadChannel {
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

    suspend fun downloadJson(url: String, timeout: Long = 30000): String {
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

    suspend inline fun <reified T> handleResponse(
        showLoading: Boolean, response: io.ktor.client.statement.HttpResponse
    ): Result<GenericResponse<T>> {

        if (showLoading) {
            PopupHandler.hideLoading()
        }

        if (response.status.value != 200 && response.status.value != 204) {
            println("HTTP Error: ${response.bodyAsText()}")
            val error = response.body<ErrorResponse>()
            PopupHandler.displayAlert(error.title, error.detail)
            return Result.failure(Exception(error.detail))
        }

        return Result.success(
            GenericResponse(
                obj = response.body<T>(),
                cookies = response.setCookie().map { it.name + ":" + it.value },
                status = response.status.value,
                headers = response.headers.entries().associate { entry ->
                    entry.key to entry.value
                })
        )
    }
}
