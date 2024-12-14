package com.sweetmesoft.kmplibrary.tools

import kotlin.math.PI

expect fun Double.toCurrency(): String

fun Double.toRadians(): Double = this * PI / 180.0