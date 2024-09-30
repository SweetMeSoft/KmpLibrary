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
        internal var listShow by mutableStateOf(false)
        internal var listTitle by mutableStateOf("")
        internal var listMessage by mutableStateOf("")
        internal var listOptions by mutableStateOf(listOf<String>())
        internal var listAccept: (String) -> Unit by mutableStateOf({})
        internal var listDismiss by mutableStateOf({})

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
            accept: () -> Unit,
            dismiss: () -> Unit = {},
        ) {
            confirmTitle = title
            confirmMessage = message
            confirmAcceptText = confirmText
            confirmCancelText = cancelText
            confirmDismiss = { dismiss() }
            confirmAccept = { accept() }
            confirmShow = true
        }

        fun displayAlertList(
            title: String,
            message: String,
            options: List<String> = listOf(),
            accept: (String) -> Unit,
            dismiss: () -> Unit = {},
        ) {
            listTitle = title
            listMessage = message
            listOptions = options
            listAccept = {
                accept(it)
                listShow = false
            }
            listDismiss = {
                dismiss()
                listShow = false
            }
            listShow = true
        }
    }
}