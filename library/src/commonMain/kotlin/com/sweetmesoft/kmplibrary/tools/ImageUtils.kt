package com.sweetmesoft.kmplibrary.tools

import androidx.compose.ui.graphics.ImageBitmap

expect fun resizeImage(imageBitmap: ImageBitmap, maxSize: Int): ImageBitmap

expect fun resizeImage(bytes: ByteArray, maxSize: Int): ByteArray

expect fun rotateImage(byteArray: ByteArray, angle: Int): ByteArray

expect fun rotateImage(imageBitmap: ImageBitmap, angle: Int): ImageBitmap

expect fun imageToBase64(imageBitmap: ImageBitmap): String

expect fun imageToBytes(imageBitmap: ImageBitmap): ByteArray