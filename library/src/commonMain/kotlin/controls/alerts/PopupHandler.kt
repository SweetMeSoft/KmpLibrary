package controls.alerts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PopupHandler {
    companion object {
        var isLoading by mutableStateOf(false)
        internal var alertShow by mutableStateOf(false)
        internal var alertTitle by mutableStateOf("")
        internal var alertAcceptText by mutableStateOf("")
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
        internal var listAcceptText by mutableStateOf("")
        internal var listCancelText by mutableStateOf("")
        internal var listTitle by mutableStateOf("")
        internal var listMessage by mutableStateOf("")
        internal var listOptions by mutableStateOf(listOf<String>())
        internal var listAccept: (String) -> Unit by mutableStateOf({})
        internal var listDismiss by mutableStateOf({})
        internal var promptShow by mutableStateOf(false)
        internal var promptTitle by mutableStateOf("")
        internal var promptAcceptText by mutableStateOf("")
        internal var promptSubtitle by mutableStateOf("")
        internal var promptInput by mutableStateOf("")
        internal var promptAccept: (String) -> Unit by mutableStateOf({})
        internal var promptDismiss by mutableStateOf({})

        fun displayAlert(title: String, message: String, dismiss: () -> Unit = {}) {
            alertTitle = title
            alertMessage = message
            alertAcceptText = "Aceptar"
            alertDismiss = dismiss
            alertShow = true
        }

        fun displayConfirm(
            title: String,
            message: String,
            confirmText: String = "Aceptar",
            cancelText: String = "Cancelar",
            dismiss: () -> Unit = {},
            accept: () -> Unit
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
            confirmText: String = "Aceptar",
            cancelText: String = "Cancelar",
            options: List<String> = listOf(),
            dismiss: () -> Unit = {},
            accept: (String) -> Unit
        ) {
            listTitle = title
            listMessage = message
            listAcceptText = confirmText
            listCancelText = cancelText
            listOptions = options
            listAccept = { accept(it) }
            listDismiss = { dismiss() }
            listShow = true
        }

        fun displayPrompt(
            title: String,
            subtitle: String,
            confirmText: String = "Aceptar",
            input: String,
            dismiss: () -> Unit = {},
            accept: (String) -> Unit
        ) {
            promptTitle = title
            promptSubtitle = subtitle
            promptAcceptText = "Aceptar"
            promptInput = input
            promptAccept = { accept(it) }
            promptDismiss = { dismiss() }
            promptShow = true
        }
    }
}