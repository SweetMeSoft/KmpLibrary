package com.sweetmesoft.kmplibrary.tools

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSLocale
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.UIKit.UIApplication

actual fun getCurrentLanguage(): String {
    return NSLocale.currentLocale.languageCode
}

actual fun openUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url)
    if (nsUrl != null) {
        UIApplication.sharedApplication.openURL(
            nsUrl,
            mapOf<Any?, Any?>(),
            null
        )
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
fun ByteArray.toNSData(): NSData = usePinned { pinned ->
    NSData.create(
        bytes = pinned.addressOf(0),
        length = this.size.convert()
    )
}