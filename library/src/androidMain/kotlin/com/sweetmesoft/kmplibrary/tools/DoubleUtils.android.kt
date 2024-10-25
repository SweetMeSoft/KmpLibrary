package com.sweetmesoft.kmplibrary.tools

import java.text.DecimalFormat

actual fun Double.toCurrency(): String {
    val decimalFormat = DecimalFormat("$ #,##0.00")
    return decimalFormat.format(this)
}