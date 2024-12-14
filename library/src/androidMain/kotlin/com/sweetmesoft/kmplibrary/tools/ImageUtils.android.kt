package com.sweetmesoft.kmplibrary.tools

import android.graphics.Bitmap
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.ByteArrayOutputStream

actual fun imageToBase64(imageBitmap: ImageBitmap): String {
    val byteArray = imageToBytes(imageBitmap)
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

actual fun resizeImage(imageBitmap: ByteArray, maxSize: Int): ByteArray {
    TODO("Not yet implemented")
}

actual fun imageToBytes(imageBitmap: ImageBitmap): ByteArray {
    val bitmap: Bitmap = imageBitmap.asAndroidBitmap()
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    return outputStream.toByteArray()
}