package com.sweetmesoft.kmplibrary.tools

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSData
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.dataWithBytes
import platform.Foundation.writeToURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.popoverPresentationController

@OptIn(ExperimentalForeignApi::class)
actual fun shareFile(bytes: ByteArray, fileName: String) {
    val data = bytes.usePinned { pinned ->
        NSData.dataWithBytes(pinned.addressOf(0), bytes.size.toULong())
    }

    val tempDir = NSTemporaryDirectory()
    val filePath = tempDir + fileName
    val fileUrl = NSURL.fileURLWithPath(filePath)

    val success = data.writeToURL(fileUrl, true)
    if (!success) {
        throw IllegalStateException("Failed to write file to $filePath")
    }

    val activityViewController =
        UIActivityViewController(activityItems = listOf(fileUrl), applicationActivities = null)

    val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootViewController?.presentViewController(
        activityViewController,
        animated = true,
        completion = null
    )

    // Configuración de popover para iPad
    val popover = activityViewController.popoverPresentationController
    if (popover != null) {
        popover.sourceView = rootViewController?.view // Usa la vista principal como origen
        val bounds = rootViewController?.view?.bounds?.useContents {
            CGRectMake(
                size.width / 2.0,
                size.height / 2.0,
                0.0,
                0.0
            )
        }
        popover.sourceRect = bounds ?: CGRectMake(0.0, 0.0, 0.0, 0.0)
        popover.permittedArrowDirections = 0u
    }
}