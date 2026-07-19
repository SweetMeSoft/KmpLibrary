package com.sweetmesoft.kmpbase.tools

enum class BiometricStatus {
    AVAILABLE,
    NOT_AVAILABLE,
    NOT_ENROLLED,
    SUPPORTED_BUT_DISABLED,
    UNKNOWN_ERROR
}

expect fun isBiometricAvailable(): BiometricStatus

expect suspend fun authenticateBiometric(
    title: String,
    subtitle: String,
    negativeButtonText: String
): Result<Boolean>
