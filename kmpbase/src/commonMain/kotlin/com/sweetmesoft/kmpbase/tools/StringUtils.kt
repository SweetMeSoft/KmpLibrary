package com.sweetmesoft.kmpbase.tools

fun String.isGuid(): Boolean {
    val regex =
        Regex("^[{]?[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}[}]?\$")
    return this.matches(regex)
}

fun String.isNotGuid(): Boolean {
    return !this.isGuid()
}