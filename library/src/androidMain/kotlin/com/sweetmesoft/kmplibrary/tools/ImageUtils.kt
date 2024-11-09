package com.sweetmesoft.kmplibrary.tools

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap

actual fun resizeImage(imageBitmap: ImageBitmap, maxSize: Int): ImageBitmap {
    val originalBitmap = imageBitmap.asAndroidBitmap()
    val height = (originalBitmap.height * maxSize) / originalBitmap.width
    val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, maxSize, height, true)
    return resizedBitmap.asImageBitmap()
}