package com.sweetmesoft.kmpcontrols.utils

import kotlin.math.PI

fun Double.toRadians(): Double = this * PI / 180.0

fun Double.toDegrees(): Double = this * 180.0 / PI

fun Float.toDegrees(): Float = (this * 180f / PI).toFloat()