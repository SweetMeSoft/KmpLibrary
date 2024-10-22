package com.sweetmesoft.kmplibrary.tools

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

expect fun getCurrentLanguage(): String

fun String.isEmail(): Boolean {
    val emailRegex = Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)")
    return this.matches(emailRegex)
}

fun String.isNotEmail(): Boolean {
    return !this.isEmail()
}

fun isValidPassword(password: String): Boolean {
    val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?#&.,:;_\\-])[A-Za-z\\d@\$!%*?#&.,:;_\\-]{8,}$")
    return passwordRegex.matches(password)
}

fun String.isGuid(): Boolean {
    val regex =
        Regex("^[{]?[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}[}]?\$")
    return this.matches(regex)
}

fun String.isNotGuid(): Boolean {
    return !this.isGuid()
}

@OptIn(ExperimentalUuidApi::class)
fun Uuid.isEmpty(): Boolean {
    return this == Uuid.NIL
}

@OptIn(ExperimentalUuidApi::class)
fun Uuid.isNotEmpty(): Boolean {
    return this != Uuid.NIL
}


val emptyFunction: () -> Unit = {}
