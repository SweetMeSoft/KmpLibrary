package tools

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


val emptyFunction: () -> Unit = {}
