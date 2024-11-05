package com.sweetmesoft.kmplibrary.tools

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle

actual fun Double.toCurrency(): String {
    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterCurrencyStyle
        minimumFractionDigits = 2u
        maximumFractionDigits = 2u
        groupingSeparator = "."
        decimalSeparator = ","
        currencySymbol = "$ "
        usesGroupingSeparator = true
    }
    return formatter.stringFromNumber(NSNumber(this)) ?: "$this"
}