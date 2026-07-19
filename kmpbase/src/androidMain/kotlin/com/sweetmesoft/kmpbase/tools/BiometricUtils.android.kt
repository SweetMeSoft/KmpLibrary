package com.sweetmesoft.kmpbase.tools

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.sweetmesoft.kmpbase.BaseAndroid.Companion.getContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual fun isBiometricAvailable(): BiometricStatus {
    val context = getContext()
    val biometricManager = BiometricManager.from(context)
    return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
        BiometricManager.BIOMETRIC_SUCCESS -> BiometricStatus.AVAILABLE
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> BiometricStatus.NOT_AVAILABLE
        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> BiometricStatus.SUPPORTED_BUT_DISABLED
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> BiometricStatus.NOT_ENROLLED
        else -> BiometricStatus.UNKNOWN_ERROR
    }
}

actual suspend fun authenticateBiometric(
    title: String,
    subtitle: String,
    negativeButtonText: String
): Result<Boolean> = suspendCancellableCoroutine { continuation ->
    val context = getContext()
    if (context !is FragmentActivity) {
        continuation.resume(Result.failure(IllegalStateException("Activity is not a FragmentActivity")))
        return@suspendCancellableCoroutine
    }

    val executor = ContextCompat.getMainExecutor(context)
    val biometricPrompt = BiometricPrompt(context, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_USER_CANCELED || errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    continuation.resume(Result.success(false))
                } else {
                    continuation.resume(Result.failure(Exception(errString.toString())))
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                continuation.resume(Result.success(true))
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }
        })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(title)
        .setSubtitle(subtitle)
        .setNegativeButtonText(negativeButtonText)
        .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        .build()

    biometricPrompt.authenticate(promptInfo)
}
