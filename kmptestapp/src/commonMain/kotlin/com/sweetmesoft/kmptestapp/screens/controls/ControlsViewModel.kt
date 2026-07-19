package com.sweetmesoft.kmptestapp.screens.controls

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.sweetmesoft.kmpbase.base.BaseViewModel
import com.sweetmesoft.kmpbase.controls.alerts.PopupHandler
import com.sweetmesoft.kmpbase.tools.BiometricStatus
import com.sweetmesoft.kmpbase.tools.authenticateBiometric
import com.sweetmesoft.kmpbase.tools.isBiometricAvailable
import kotlinx.coroutines.launch

class ControlsViewModel : BaseViewModel() {
    var searchQuery by mutableStateOf("")
    var password by mutableStateOf("")
    var clickableText by mutableStateOf("Click Me!")
    var otpValue by mutableStateOf("")

    fun onOtpComplete(otp: String) {
        viewModelScope.launch {
            PopupHandler.displayAlert(
                title = "OTP Completed",
                message = "Entered OTP code: $otp"
            )
        }
    }

    fun testBiometrics() {
        viewModelScope.launch {
            val status = isBiometricAvailable()
            if (status != BiometricStatus.AVAILABLE) {
                PopupHandler.displayAlert(
                    title = "Biometrics Status",
                    message = "Biometrics status is: $status"
                )
                return@launch
            }

            PopupHandler.showLoading()
            val result = authenticateBiometric(
                title = "Verify Identity",
                subtitle = "Please authenticate to continue",
                negativeButtonText = "Cancel"
            )
            PopupHandler.hideLoading()

            result.onSuccess { authenticated ->
                PopupHandler.displayAlert(
                    title = "Authentication Result",
                    message = if (authenticated) "Authentication Succeeded!" else "Authentication Cancelled"
                )
            }.onFailure { error ->
                PopupHandler.displayAlert(
                    title = "Authentication Error",
                    message = error.message ?: "Unknown error occurred"
                )
            }
        }
    }
}
