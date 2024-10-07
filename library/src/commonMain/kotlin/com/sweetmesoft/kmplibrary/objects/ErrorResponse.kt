package com.sweetmesoft.kmplibrary.objects

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val title: String = "",
    val status: Int = 0,
    val detail: String = "",
    val instance: String = "",
    val type: String = ""
)
