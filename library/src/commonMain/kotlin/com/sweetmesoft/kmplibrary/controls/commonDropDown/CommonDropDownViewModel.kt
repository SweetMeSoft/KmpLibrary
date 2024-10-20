package com.sweetmesoft.kmplibrary.controls.commonDropDown

import com.sweetmesoft.kmplibrary.base.BaseViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

class CommonDropDownViewModel(
    val list: List<Any>,
    val textProperty: String = "",
    val idProperty: String = ""
) :
    BaseViewModel() {

    inline fun <reified T> convertToCommonDropDownItem(registers: List<T>): List<CommonDropDownItem> {
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
}