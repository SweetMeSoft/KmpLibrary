package com.sweetmesoft.kmplibrary.tools

expect fun getPlatform(): PlatformType
expect fun getAppVersion(): String
expect fun openAppStore(appId: String)

enum class PlatformType {
    ANDROID,
    IOS,
    UNKNOWN
}