package com.sweetmesoft.kmpbase.tools

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSError
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthenticationWithBiometrics
import platform.LocalAuthentication.LAErrorAuthenticationFailed
import platform.LocalAuthentication.LAErrorUserCancel
import platform.LocalAuthentication.LAErrorBiometryNotAvailable
import platform.LocalAuthentication.LAErrorBiometryNotEnrolled
import platform.LocalAuthentication.LAErrorBiometryLockout
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
actual fun isBiometricAvailable(): BiometricStatus {
    val context = LAContext()
    memScoped {
        val errorRef = alloc<ObjCObjectVar<NSError?>>()
        val canEvaluate = context.canEvaluatePolicy(LAPolicyDeviceOwnerAuthenticationWithBiometrics, errorRef.ptr)
        if (canEvaluate) {
            return BiometricStatus.AVAILABLE
        }
        val error = errorRef.value
        if (error != null) {
            return when (error.code) {
                LAErrorBiometryNotAvailable -> BiometricStatus.NOT_AVAILABLE
                LAErrorBiometryNotEnrolled -> BiometricStatus.NOT_ENROLLED
                LAErrorBiometryLockout -> BiometricStatus.SUPPORTED_BUT_DISABLED
                else -> BiometricStatus.UNKNOWN_ERROR
            }
        }
        return BiometricStatus.NOT_AVAILABLE
    }
}

actual suspend fun authenticateBiometric(
    title: String,
    subtitle: String,
    negativeButtonText: String
): Result<Boolean> = suspendCoroutine { continuation ->
    val context = LAContext()
    context.localizedFallbackTitle = negativeButtonText
    
    context.evaluatePolicy(
        policy = LAPolicyDeviceOwnerAuthenticationWithBiometrics,
        localizedReason = subtitle,
        reply = { success, error ->
            if (success) {
                continuation.resume(Result.success(true))
            } else {
                if (error != null) {
                    if (error.code == LAErrorUserCancel) {
                        continuation.resume(Result.success(false))
                    } else {
                        continuation.resume(Result.failure(Exception(error.localizedDescription)))
                    }
                } else {
                    continuation.resume(Result.success(false))
                }
            }
        }
    )
}
