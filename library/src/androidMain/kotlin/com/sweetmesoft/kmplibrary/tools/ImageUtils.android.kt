package com.sweetmesoft.kmplibrary.tools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

actual fun imageToBase64(imageBitmap: ImageBitmap): String {
    val byteArray = imageToBytes(imageBitmap)
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

actual fun resizeImage(bytes: ByteArray, maxSize: Int): ByteArray {
    val originalBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    val aspectRatio = originalBitmap.width.toFloat() / originalBitmap.height
    val width: Int
    val height: Int
    if (originalBitmap.width > originalBitmap.height) {
        width = maxSize
        height = (maxSize / aspectRatio).toInt()
    } else {
        height = maxSize
        width = (maxSize * aspectRatio).toInt()
    }
    val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, true)
    val outputStream = ByteArrayOutputStream()
    resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

    return outputStream.toByteArray()
}

actual fun resizeImage(imageBitmap: ImageBitmap, maxSize: Int): ImageBitmap {
    val originalBitmap = imageBitmap.asAndroidBitmap()
    val aspectRatio = originalBitmap.width.toFloat() / originalBitmap.height
    val width: Int
    val height: Int
    if (originalBitmap.width > originalBitmap.height) {
        width = maxSize
        height = (maxSize / aspectRatio).toInt()
    } else {
        height = maxSize
        width = (maxSize * aspectRatio).toInt()
    }
    val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, true)
    return resizedBitmap.asImageBitmap()
}

actual fun imageToBytes(imageBitmap: ImageBitmap): ByteArray {
    val bitmap: Bitmap = imageBitmap.asAndroidBitmap()
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    return outputStream.toByteArray()
}

actual fun rotateImage(byteArray: ByteArray, angle: Int): ByteArray {
    val originalBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    val matrix = Matrix().apply {
        postRotate(angle.toFloat())
    }
    val rotatedBitmap = Bitmap.createBitmap(
        originalBitmap,
        0, 0,
        originalBitmap.width, originalBitmap.height,
        matrix,
        true
    )
    val outputStream = ByteArrayOutputStream()
    rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    return outputStream.toByteArray()
}

actual fun rotateImage(
    imageBitmap: ImageBitmap,
    angle: Int
): ImageBitmap {
    val originalBitmap: Bitmap = imageBitmap.asAndroidBitmap()
    val matrix = Matrix().apply {
        postRotate(angle.toFloat())
    }
    val rotatedBitmap = Bitmap.createBitmap(
        originalBitmap,
        0, 0,
        originalBitmap.width, originalBitmap.height,
        matrix,
        true
    )
    return rotatedBitmap.asImageBitmap()
}