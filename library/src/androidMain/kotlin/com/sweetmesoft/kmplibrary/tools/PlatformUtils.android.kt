package com.sweetmesoft.kmplibrary.tools

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.sweetmesoft.kmplibrary.BaseAndroid.Companion.getContext

actual fun getPlatform(): PlatformType {
    return PlatformType.ANDROID
}

actual fun getAppVersion(): String {
    return try {
        val context = getContext()
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName ?: "Unknown"
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
    }
}

actual fun openAppStore(appId: String) {
    val context = getContext()
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("market://details?id=$appId")
        setPackage("com.android.vending")
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}