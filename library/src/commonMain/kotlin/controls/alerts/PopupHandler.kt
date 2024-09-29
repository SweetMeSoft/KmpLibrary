package controls.alerts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PopupHandler {
    companion object {
        var isLoading by mutableStateOf(false)
        internal var alertShow by mutableStateOf(false)
        internal var alertTitle by mutableStateOf("")
        internal var alertMessage by mutableStateOf("")
        internal var alertDismiss by mutableStateOf({})
        internal var confirmShow by mutableStateOf(false)
        internal var confirmTitle by mutableStateOf("")
        internal var confirmMessage by mutableStateOf("")
        internal var confirmAcceptText by mutableStateOf("")
        internal var confirmCancelText by mutableStateOf("")
        internal var confirmAccept by mutableStateOf({})
        internal var confirmDismiss by mutableStateOf({})

        fun displayAlert(title: String, message: String, dismiss: () -> Unit = {}) {
            alertTitle = title
            alertMessage = message
            alertDismiss = dismiss
            alertShow = true
        }

        fun displayConfirm(
            title: String,
            message: String,
            confirmText: String = "Confirmar",
            cancelText: String = "Cancelar",
            onConfirm: () -> Unit,
            hide: () -> Unit = {},
        ) {
            confirmTitle = title
            confirmMessage = message
            confirmAcceptText = confirmText
            confirmCancelText = cancelText
            confirmDismiss = { hide() }
            confirmAccept = { onConfirm() }
            confirmShow = true
        }
//
//        fun displayAlertList(
//            title: String,
//            message: String,
//            options: List<String> = listOf(),
//            accept: (String) -> Unit,
//            hide: () -> Unit = {},
//        ) {
//            BaseViewModel2.title = title
//            BaseViewModel2.message = message
//            BaseViewModel2.options = options
//            onConfirmList = {
//                accept(it)
//                showAlertList = false
//            }
//            BaseViewModel2.hide = {
//                hide()
//                showAlertList = false
//            }
//            showAlertList = true
//        }
    }
}