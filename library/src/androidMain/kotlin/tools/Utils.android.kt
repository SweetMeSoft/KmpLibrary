package tools

import java.util.Locale

actual fun getCurrentLanguage(): String {
    return Locale.getDefault().language
}