package com.sweetmesoft.kmplibrary.controls.commonList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.sweetmesoft.kmplibrary.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tools.NetworkUtils.get

class CommonListViewModel(
    val url: String = "",
    val list: List<Any> = listOf()
) :
    BaseViewModel() {
    var state by mutableStateOf(CommonListState())

    data class CommonListState(
        val isLoading: Boolean = true,
        val list: List<Any> = listOf()
    )

    inline fun <reified T : Any> start() {
        if (url.isNotEmpty()) {
            viewModelScope.launch {
                val result = get<List<T>>(url, false)
                result.onSuccess {
                    state = state.copy(
                        list = it,
                        isLoading = false
                    )
                }
            }
        } else {
            viewModelScope.launch {
                delay(200)
                state = state.copy(
                    list = list,
                    isLoading = false
                )
            }
        }
    }
}