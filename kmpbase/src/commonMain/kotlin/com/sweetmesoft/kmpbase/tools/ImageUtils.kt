package com.sweetmesoft.kmpbase.tools

import androidx.compose.ui.graphics.ImageBitmap

expect fun resizeImage(imageBitmap: ImageBitmap, maxSize: Int): ImageBitmap

expect fun resizeImage(byteArray: ByteArray, maxSize: Int): ByteArray

expect fun rotateImage(byteArray: ByteArray, angle: Int): ByteArray

expect fun rotateImage(imageBitmap: ImageBitmap, angle: Int): ImageBitmap

expect fun ImageBitmap.toBase64(): String

expect fun ByteArray.toImageBitmap(): ImageBitmap

expect fun ImageBitmap.toByteArray(): ByteArray