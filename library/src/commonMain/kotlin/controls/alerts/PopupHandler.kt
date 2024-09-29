package controls.alerts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PopupHandler {
    companion object {
        var isLoading: Boolean = false
        internal var alertShow by mutableStateOf(false)
        internal var alertTitle by mutableStateOf("")
        internal var alertMessage by mutableStateOf("")
        internal var alertDismiss by mutableStateOf({})

        fun displayAlert(title: String, message: String, dismiss: () -> Unit = {}) {
            alertTitle = title
            alertMessage = message
            alertDismiss = dismiss
            alertShow = true
        }
    }
}