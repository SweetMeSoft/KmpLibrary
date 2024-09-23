package controls.commonDropDown

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import base.BaseViewModel
import kotlinx.coroutines.launch
import tools.NetworkUtils.get

class CustomDropDownViewModel(
    private val url: String,
    private val list: List<Any>,
) :
    BaseViewModel() {
    var state by mutableStateOf(CommonDropDownState())
        private set

    data class CommonDropDownState(
        val expanded: Boolean = false,
        val list: List<Any> = listOf(),
        val isLoading: Boolean = true,
        val value: String = ""
    )

    internal inline fun <reified T : Any> start() {
        if (list.any()) {
            state = state.copy(
                list = list,
                isLoading = false
            )
        } else {
            if (url.isNotEmpty()) {
                viewModelScope.launch {
                    val registers = get<List<T>>(url)
                    state = state.copy(
                        list = registers,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun clicked() {
        state = state.copy(expanded = !state.expanded)
    }

    fun selectValue(it: String) {

    }
}
