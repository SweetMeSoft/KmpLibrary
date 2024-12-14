package com.sweetmesoft.kmplibrary.tools

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.dataWithBytes
import platform.Foundation.writeToURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

@OptIn(ExperimentalForeignApi::class)
actual fun shareFile(bytes: ByteArray, fileName: String) {
    val data = bytes.usePinned { pinned ->
        NSData.dataWithBytes(pinned.addressOf(0), bytes.size.toULong())
    }

    val tempDir = NSTemporaryDirectory()
    val filePath = tempDir + fileName
    val fileUrl = NSURL.fileURLWithPath(filePath)

    val success = data.writeToURL(fileUrl, true)

    val activityViewController =
        UIActivityViewController(activityItems = listOf(fileUrl), applicationActivities = null)

    val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootViewController?.presentViewController(
        activityViewController,
        animated = true,
        completion = null
    )
}