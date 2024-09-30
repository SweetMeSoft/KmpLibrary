package tools

expect fun getCurrentLanguage(): String

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)")
    return emailRegex.matches(email)
}

fun isValidPassword(password: String): Boolean {
    val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?#&.,:;_\\-])[A-Za-z\\d@\$!%*?#&.,:;_\\-]{8,}$")
    return passwordRegex.matches(password)
}

val emptyFunction: () -> Unit = {}
