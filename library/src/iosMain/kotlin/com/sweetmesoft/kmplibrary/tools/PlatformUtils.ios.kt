package com.sweetmesoft.kmplibrary.tools

actual fun getPlatform(): PlatformType {
    return PlatformType.IOS
}

actual fun getAppVersion(): String {
    val version =
        NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String
    return version ?: "Unknown"
}

actual fun openAppStore(appId: String) {
    val appStoreUrl = "https://apps.apple.com/app/id$appId"
    val nsUrl = NSURL.URLWithString(appStoreUrl)!!
    UIApplication.sharedApplication.openURL(nsUrl)
}