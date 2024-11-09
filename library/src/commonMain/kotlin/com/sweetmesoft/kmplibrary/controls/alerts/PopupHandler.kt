package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kmp_library.library.generated.resources.Accept
import kmp_library.library.generated.resources.Cancel
import kmp_library.library.generated.resources.Downloading
import kmp_library.library.generated.resources.Res
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

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
        internal var progressShow by mutableStateOf(false)
        internal var progressTitle by mutableStateOf("")
        internal var progressCancelText by mutableStateOf("")
        internal var progressProgress by mutableStateOf(0.0f)
        internal var progressDismiss by mutableStateOf({})

        suspend fun displayAlert(
            title: String,
            message: String,
            acceptText: String = "",
            dismiss: () -> Unit = {}
        ) {
            alertTitle = title
            alertMessage = message
            alertAcceptText = acceptText.ifEmpty { getString(Res.string.Accept) }
            alertDismiss = dismiss
            alertShow = true
        }

        suspend fun displayConfirm(
            title: String,
            message: String,
            confirmText: String = "",
            cancelText: String = "",
            dismiss: () -> Unit = {},
            accept: () -> Unit
        ) {
            confirmTitle = title
            confirmMessage = message
            confirmAcceptText = confirmText.ifEmpty { getString(Res.string.Accept) }
            confirmCancelText = cancelText.ifEmpty { getString(Res.string.Cancel) }
            confirmDismiss = { dismiss() }
            confirmAccept = { accept() }
            confirmShow = true
        }

        suspend fun displayAlertList(
            title: String,
            message: String,
            confirmText: String = "",
            cancelText: String = "",
            options: List<String> = listOf(),
            dismiss: () -> Unit = {},
            accept: (String) -> Unit
        ) {
            listTitle = title
            listMessage = message
            listAcceptText = confirmText.ifEmpty { getString(Res.string.Accept) }
            listCancelText = cancelText.ifEmpty { getString(Res.string.Cancel) }
            listOptions = options
            listAccept = { accept(it) }
            listDismiss = { dismiss() }
            listShow = true
        }

        suspend fun displayPrompt(
            title: String,
            subtitle: String,
            confirmText: String = "",
            input: String,
            dismiss: () -> Unit = {},
            accept: (String) -> Unit
        ) {
            promptTitle = title
            promptSubtitle = subtitle
            promptAcceptText = confirmText.ifEmpty { getString(Res.string.Accept) }
            promptInput = input
            promptAccept = { accept(it) }
            promptDismiss = { dismiss() }
            promptShow = true
        }

        suspend fun displayProgress(
            title: String = "Descargando",
            cancelText: String = "Cancelar",
            dismiss: () -> Unit = {},
        ) {
            progressTitle = title.ifEmpty { getString(Res.string.Downloading) }
            progressCancelText = cancelText.ifEmpty { getString(Res.string.Cancel) }
            progressProgress = 0f
            progressDismiss = { dismiss() }
            progressShow = true
        }
    }
}