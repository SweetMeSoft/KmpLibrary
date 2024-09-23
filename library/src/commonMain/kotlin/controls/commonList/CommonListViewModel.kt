package controls.commonList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tools.NetworkUtils.get

class CommonListViewModel(
    private val url: String = "",
    private val list: List<Any> = listOf()
) :
    BaseViewModel() {
    var state by mutableStateOf(CommonListState())
        private set

    data class CommonListState(
        val isLoading: Boolean = true,
        val list: List<Any> = listOf(),
        val animation: String = ""
    )

    internal inline fun <reified T : Any> start() {
        if (url.isNotEmpty()) {
            viewModelScope.launch {
                val registers = get<List<T>>(url)
                state = state.copy(
                    list = registers,
                    isLoading = false,
                    animation = ""//Res.readBytes("drawable/empty.json").decodeToString()
                )
            }
        } else {
            viewModelScope.launch {
                delay(200)
                state = state.copy(
                    list = list,
                    isLoading = false,
                    animation = ""//Res.readBytes("drawable/empty.json").decodeToString()
                )
            }
        }
    }
}