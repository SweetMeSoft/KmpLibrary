package com.sweetmesoft.kmplibrary.tools

import platform.Foundation.NSLocale
import platform.Foundation.NSURL
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.UIKit.UIApplication

actual fun getCurrentLanguage(): String {
    return NSLocale.currentLocale.languageCode ?: "en"
}

actual fun openUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url)
    if (nsUrl != null) {
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}
