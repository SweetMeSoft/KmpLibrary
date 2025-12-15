package com.sweetmesoft.kmpbase.tools

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

@OptIn(ExperimentalUuidApi::class)
fun Uuid.isEmpty(): Boolean {
    return this == Uuid.NIL
}

@OptIn(ExperimentalUuidApi::class)
fun Uuid.isNotEmpty(): Boolean {
    return this != Uuid.NIL
}

expect fun ByteArray.toBase64(): String

expect fun openUrl(url: String)

val emptyFunction: () -> Unit = {}