package com.sweetmesoft.kmplibrary.tools

import android.content.Intent
import android.net.Uri
import com.sweetmesoft.kmplibrary.BaseAndroid
import java.util.Locale

actual fun getCurrentLanguage(): String {
    return Locale.getDefault().language
}

actual fun openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    val context = BaseAndroid.getContext()
    context.startActivity(intent)
}
