package com.sweetmesoft.kmplibrary.tools

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle

actual fun Double.toCurrency(): String {
    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterCurrencyStyle
        minimumFractionDigits = 2
        maximumFractionDigits = 2
        groupingSeparator = "."
        decimalSeparator = ","
        currencySymbol = "$ "
        // Asegurarse de que se utilice el separador de miles
        usesGroupingSeparator = true
    }
    return formatter.stringFromNumber(NSNumber(this)) ?: "$this"
}