package com.sweetmesoft.kmplibrary.tools

actual fun Double.toCurrency(): String {
    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterCurrencyStyle
    }
    return formatter.stringFromNumber(numberWithDouble(amount)) ?: "$amount"
}