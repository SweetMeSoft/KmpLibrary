package com.sweetmesoft.kmptestapp

import kotlinx.serialization.Serializable

@Serializable
data class PhotoProfileRequest(
    val base64: String
)