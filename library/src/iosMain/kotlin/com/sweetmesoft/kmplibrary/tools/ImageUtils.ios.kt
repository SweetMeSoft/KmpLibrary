package com.sweetmesoft.kmplibrary.tools

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGFloat
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSData
import platform.Foundation.base64EncodedStringWithOptions
import platform.UIKit.UIGraphicsBeginImageContext
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation

actual fun imageToBase64(imageBitmap: ImageBitmap): String {
    val uiImage = imageBitmap.toUIImage()
    val imageData = uiImage?.PNGRepresentation()
        ?: throw IllegalArgumentException("Failed to encode image to PNG")
    return imageData.base64EncodedStringWithOptions(0u)
}

@OptIn(ExperimentalForeignApi::class)
actual fun resizeImage(bytes: ByteArray, maxSize: Int): ByteArray {
    require(maxSize > 0) { "maxSize must be greater than 0" }
    val data = bytes.toNSData()
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

@OptIn(ExperimentalForeignApi::class)
actual fun resizeImage(imageBitmap: ImageBitmap, maxSize: Int): ImageBitmap {
    val uiImage: UIImage = imageBitmap.toUIImage() ?: return imageBitmap
    val originalWidth = uiImage.size.useContents { width }
    val originalHeight = uiImage.size.useContents { height }
    val aspectRatio = originalWidth / originalHeight
    val newWidth: Double
    val newHeight: Double

    if (originalWidth > originalHeight) {
        newWidth = maxSize.toDouble()
        newHeight = newWidth / aspectRatio
    } else {
        newHeight = maxSize.toDouble()
        newWidth = newHeight * aspectRatio
    }

    UIGraphicsBeginImageContext(CGSizeMake(newWidth, newHeight))
    uiImage.drawInRect(CGRectMake(0.0, 0.0, newWidth, newHeight))
    val resizedUIImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()
    return resizedUIImage?.toImageBitmap() ?: imageBitmap
}

actual fun imageToBytes(imageBitmap: ImageBitmap): ByteArray {
    val uiImage = imageBitmap.toUIImage()
    val imageData = uiImage?.PNGRepresentation()
        ?: throw IllegalArgumentException("Failed to encode image to PNG")

    return imageData.toByteArray()
}

// Extensión para obtener PNG data de UIImage
private fun UIImage.PNGRepresentation(): NSData? {
    return UIImagePNGRepresentation(this)
}

actual fun rotateImage(byteArray: ByteArray, angle: Int): ByteArray {
    val data = byteArray.toNSData()
    val originalImage = UIImage(data = data)

    val rotatedImage = originalImage.rotate(angle)
        ?: throw IllegalStateException("Failed to rotate image")

    val rotatedImageData = rotatedImage.PNGRepresentation()
        ?: throw IllegalStateException("Failed to convert rotated image to PNG")
    return rotatedImageData.toByteArray()
}

actual fun rotateImage(
    imageBitmap: ImageBitmap,
    angle: Int
): ImageBitmap {
    val uiImage = imageBitmap.toUIImage()
    val rotatedImage = uiImage?.rotate(angle)
        ?: throw IllegalStateException("Failed to rotate image")
    return rotatedImage.toImageBitmap()
}