package com.sweetmesoft.kmplibrary.tools

import androidx.compose.ui.graphics.ImageBitmap
import org.jetbrains.skia.EncodedImageFormat

actual fun imageToBase64(imageBitmap: ImageBitmap): String {
    val bytes = imageBitmap.toByteArray(EncodedImageFormat.PNG)
    return byteArrayToBase64(bytes)
}

actual fun resizeImage(imageBitmap: ByteArray, maxSize: Int): ByteArray {
    TODO("Not yet implemented")
}

actual fun imageToBytes(imageBitmap: ImageBitmap): ByteArray {
    TODO("Not yet implemented")
}