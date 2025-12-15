package com.sweetmesoft.kmpbase.tools

import android.content.Intent
import androidx.core.content.FileProvider
import com.sweetmesoft.kmpbase.BaseAndroid.Companion.getContext
import java.io.File
import java.io.FileOutputStream

actual fun shareFile(bytes: ByteArray, fileName: String) {
    val file = File(getContext().cacheDir, fileName)
    FileOutputStream(file).use {
        it.write(bytes)
    }
    val uri =
        FileProvider.getUriForFile(getContext(), "${getContext().packageName}.fileprovider", file)
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    getContext().startActivity(Intent.createChooser(intent, "Share file"))
}