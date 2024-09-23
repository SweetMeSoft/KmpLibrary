package tools

import platform.Foundation.currentLocale

actual fun getCurrentLanguage(): String {
    return NSLocale.currentLocale.languageCode ?: "en"
}