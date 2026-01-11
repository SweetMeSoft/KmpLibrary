package com.sweetmesoft.kmpbase.tools

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image
import platform.CoreGraphics.CGFloat
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSData
import platform.Foundation.base64EncodedStringWithOptions
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation

actual fun ImageBitmap.toBase64(): String {
    val uiImage = this.toUIImage()
    val imageData = uiImage?.PNGRepresentation()
        ?: throw IllegalArgumentException("Failed to encode image to PNG")
    return imageData.base64EncodedStringWithOptions(0u)
}

actual fun ByteArray.toImageBitmap(): ImageBitmap {
    val data = this.toNSData()
    return UIImage(data = data).toImageBitmap()
}

@OptIn(ExperimentalForeignApi::class)
actual fun resizeImage(byteArray: ByteArray, maxSize: Int): ByteArray {
    require(maxSize > 0) { "maxSize must be greater than 0" }
    val data = byteArray.toNSData()
    val originalImage = UIImage(data = data)
    val originalSize = originalImage.size.useContents {
        Pair(width, height)
    }
    val originalWidth = originalSize.first
    val originalHeight = originalSize.second
    val aspectRatio = originalWidth / originalHeight
    val newWidth: CGFloat
    val newHeight: CGFloat
    if (originalWidth > originalHeight) {
        newWidth = maxSize.toDouble()
        newHeight = newWidth / aspectRatio
    } else {
        newHeight = maxSize.toDouble()
        newWidth = newHeight * aspectRatio
    }

    UIGraphicsBeginImageContextWithOptions(CGSizeMake(newWidth, newHeight), false, 1.0)
    originalImage.drawInRect(CGRectMake(0.0, 0.0, newWidth, newHeight))
    val resizedImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()
    val finalImage = resizedImage ?: throw IllegalStateException("Image resizing failed")
    val compressedData = finalImage.PNGRepresentation()
        ?: throw IllegalStateException("Failed to compress resized image")

    return compressedData.toByteArray()
}

actual fun resizeImage(imageBitmap: ImageBitmap, maxSize: Int): ImageBitmap {
    val bytes = imageBitmap.toByteArray()
    val resizedBytes = resizeImage(bytes, maxSize)
    val resizedData = resizedBytes.toNSData()
    return UIImage(data = resizedData).toImageBitmap()
}

actual fun rotateImage(byteArray: ByteArray, angle: Int): ByteArray {
    val data = byteArray.toNSData()
    val originalImage = UIImage(data = data)

    val rotatedImage =
        originalImage.rotate(angle) ?: throw IllegalStateException("Failed to rotate image")

    val rotatedImageData = rotatedImage.PNGRepresentation()
        ?: throw IllegalStateException("Failed to convert rotated image to PNG")
    return rotatedImageData.toByteArray()
}

actual fun rotateImage(imageBitmap: ImageBitmap, angle: Int): ImageBitmap {
    val uiImage = imageBitmap.toUIImage()
    val rotatedImage =
        uiImage?.rotate(angle) ?: throw IllegalStateException("Failed to rotate image")
    return rotatedImage.toImageBitmap()
}

actual fun ImageBitmap.toByteArray(): ByteArray {
    return Image.makeFromBitmap(bitmap = this.asSkiaBitmap())
        .encodeToData(EncodedImageFormat.PNG, 100)?.bytes ?: byteArrayOf()
}

private fun UIImage.PNGRepresentation(): NSData? {
    return UIImagePNGRepresentation(this)
}