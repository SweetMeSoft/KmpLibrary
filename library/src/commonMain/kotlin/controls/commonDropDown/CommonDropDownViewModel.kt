package controls.commonDropDown

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import tools.NetworkUtils.get

class CommonDropDownViewModel(
    private val url: String,
    private val list: List<Any>,
    private val textProperty: String = "",
    private val idProperty: String = ""
) :
    BaseViewModel() {
    var state by mutableStateOf(CommonDropDownState())
        private set

    data class CommonDropDownState(
        val expanded: Boolean = false,
        val selectedOption: String = "",
        val list: List<CommonDropDownItem> = listOf(),
        val isLoading: Boolean = true
    )

    internal inline fun <reified T : Any> start() {
        if (list.any()) {
            val registers = list.map { it as T }
            state = state.copy(
                list = convertToCommonDropDownitem(registers),
                isLoading = false
            )
        } else {
            if (url.isNotEmpty()) {
                viewModelScope.launch {
                    val registers = get<List<T>>(url)
                    state = state.copy(
                        list = convertToCommonDropDownitem(registers),
                        isLoading = false
                    )
                }
            } else {
                state =
                    state.copy(selectedOption = "Tienes que enviar o una lista de objetos o una URL con su propiedad")
            }
        }
    }

    private inline fun <reified T> convertToCommonDropDownitem(registers: List<T>): List<CommonDropDownItem> {
        return registers.map {
            val jsonElement = Json.encodeToJsonElement(it)

            val text = jsonElement.jsonObject[textProperty]?.let { jsonValue ->
                when {
                    jsonValue is JsonPrimitive && jsonValue.isString -> jsonValue.content
                    else -> jsonValue
                }
            }

            val id = jsonElement.jsonObject[idProperty]?.let { jsonValue ->
                when {
                    jsonValue is JsonPrimitive && jsonValue.isString -> jsonValue.content
                    else -> jsonValue
                }
            }

            CommonDropDownItem(
                text = text.toString(),
                id = id.toString()
            )
        }
    }

    fun clicked() {
        state = state.copy(expanded = !state.expanded)
    }

    fun selectOption(text: String) {
        state = state.copy(selectedOption = text)
    }
}