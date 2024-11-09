package com.sweetmesoft.kmplibrary.tools

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIGraphicsBeginImageContext
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIImage
import platform.CoreGraphics.CGSizeMake
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext

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