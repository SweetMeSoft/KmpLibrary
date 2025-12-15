package com.sweetmesoft.kmplibrary.tools

import android.content.Intent
import android.net.Uri
import android.util.Base64
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

actual fun ByteArray.toBase64(): String {
    return Base64.encodeToString(this, Base64.DEFAULT)
}