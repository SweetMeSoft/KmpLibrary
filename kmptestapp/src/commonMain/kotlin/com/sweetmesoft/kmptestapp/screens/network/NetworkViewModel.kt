package com.sweetmesoft.kmptestapp.screens.network

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.sweetmesoft.kmpbase.base.BaseViewModel
import com.sweetmesoft.kmpbase.objects.ApiContentType
import com.sweetmesoft.kmpbase.tools.NetworkUtils
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val userId: Int, val id: Int, val title: String, val body: String
)

@Serializable
data class PostRequest(
    val userId: Int, val title: String, val body: String
)

class NetworkViewModel : BaseViewModel() {
    var logs by mutableStateOf("")
        private set

    private fun log(message: String) {
        logs = "$message\n\n$logs"
    }

    fun clearLogs() {
        logs = ""
    }

    fun testGet() {
        viewModelScope.launch {
            log("Testing GET...")
            val result = NetworkUtils.get<Post>(
                url = "https://jsonplaceholder.typicode.com/posts/1", showLoading = true
            )
            result.onSuccess { response ->
                log("GET Success: ${response.status}\n${response.obj}")
            }.onFailure { error ->
                log("GET Error: ${error.message}")
            }
        }
    }

    fun testPost() {
        viewModelScope.launch {
            log("Testing POST...")
            val request = PostRequest(userId = 1, title = "foo", body = "bar")
            val result = NetworkUtils.post<Post>(
                url = "https://jsonplaceholder.typicode.com/posts",
                body = request,
                showLoading = true
            )
            result.onSuccess { response ->
                log("POST Success: ${response.status}\n${response.obj}")
            }.onFailure { error ->
                log("POST Error: ${error.message}")
            }
        }
    }

    fun testPostFormUrlEncoded() {
        viewModelScope.launch {
            log("Testing POST (FormUrlEncoded)...")
            // Note: jsonplaceholder might not fully support this echo back as JSON, but we test the call structure
            val body = "userId=1&title=foo&body=bar"
            val result = NetworkUtils.post<Post>(
                url = "https://jsonplaceholder.typicode.com/posts",
                body = body,
                showLoading = true,
                contentType = ApiContentType.FormUrlEncoded
            )
            result.onSuccess { response ->
                log("POST (Form) Success: ${response.status}\n${response.obj}")
            }.onFailure { error ->
                log("POST (Form) Error: ${error.message}")
            }
        }
    }

    fun testPut() {
        viewModelScope.launch {
            log("Testing PUT...")
            val request = PostRequest(userId = 1, title = "foo updated", body = "bar updated")
            val result = NetworkUtils.put<Post>(
                url = "https://jsonplaceholder.typicode.com/posts/1",
                body = request,
                showLoading = true
            )
            result.onSuccess { response ->
                log("PUT Success: ${response.status}\n${response.obj}")
            }.onFailure { error ->
                log("PUT Error: ${error.message}")
            }
        }
    }

    fun testPatch() {
        viewModelScope.launch {
            log("Testing PATCH...")
            val request = mapOf("title" to "foo patched")
            val result = NetworkUtils.patch<Post>(
                url = "https://jsonplaceholder.typicode.com/posts/1",
                body = request,
                showLoading = true
            )
            result.onSuccess { response ->
                log("PATCH Success: ${response.status}\n${response.obj}")
            }.onFailure { error ->
                log("PATCH Error: ${error.message}")
            }
        }
    }

    fun testDelete() {
        viewModelScope.launch {
            log("Testing DELETE...")
            val result = NetworkUtils.delete<Post>(
                url = "https://jsonplaceholder.typicode.com/posts/1", showLoading = true
            )
            result.onSuccess { response ->
                log("DELETE Success: ${response.status}\n${response.obj}")
            }.onFailure { error ->
                log("DELETE Error: ${error.message}")
            }
        }
    }
}
