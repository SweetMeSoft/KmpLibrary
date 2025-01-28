package com.sweetmesoft.kmplibrary.controls.alerts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kmplibrary.library.generated.resources.Accept
import kmplibrary.library.generated.resources.Cancel
import kmplibrary.library.generated.resources.Downloading
import kmplibrary.library.generated.resources.Res
import org.jetbrains.compose.resources.getString

class PopupHandler {
    companion object {
        var accentColor: Color = Color.Unspecified
        internal var isLoading by mutableStateOf(false)
        internal var alertShow by mutableStateOf(false)
        internal var alertTitle by mutableStateOf("")
        internal var alertAcceptText by mutableStateOf("")
        internal var alertMessage by mutableStateOf("")
        internal var alertColor by mutableStateOf(Color.Unspecified)
        internal var alertDismiss by mutableStateOf({})
        internal var confirmShow by mutableStateOf(false)
        internal var confirmTitle by mutableStateOf("")
        internal var confirmMessage by mutableStateOf("")
        internal var confirmAcceptText by mutableStateOf("")
        internal var confirmCancelText by mutableStateOf("")
        internal var confirmColor by mutableStateOf(Color.Unspecified)
        internal var confirmAccept by mutableStateOf({})
        internal var confirmDismiss by mutableStateOf({})
        internal var listShow by mutableStateOf(false)
        internal var listAcceptText by mutableStateOf("")
        internal var listCancelText by mutableStateOf("")
        internal var listTitle by mutableStateOf("")
        internal var listSubtitle by mutableStateOf("")
        internal var listPlaceholder by mutableStateOf("")
        internal var listColor by mutableStateOf(Color.Unspecified)
        internal var listOptions by mutableStateOf(listOf<String>())
        internal var listAccept: (String) -> Unit by mutableStateOf({})
        internal var listDismiss by mutableStateOf({})
        internal var promptShow by mutableStateOf(false)
        internal var promptTitle by mutableStateOf("")
        internal var promptSubtitle by mutableStateOf("")
        internal var promptPlaceholder by mutableStateOf("")
        internal var promptAcceptText by mutableStateOf("")
        internal var promptCancelText by mutableStateOf("")
        internal var promptInput by mutableStateOf("")
        internal var promptColor by mutableStateOf(Color.Unspecified)
        internal var promptAccept: (String) -> Unit by mutableStateOf({})
        internal var promptDismiss by mutableStateOf({})
        internal var progressShow by mutableStateOf(false)
        internal var progressTitle by mutableStateOf("")
        internal var progressCancelText by mutableStateOf("")
        internal var progressProgress by mutableStateOf(0.0f)
        internal var progressColor by mutableStateOf(Color.Unspecified)
        internal var progressDismiss by mutableStateOf({})

        fun showLoading() {
            isLoading = true
        }

        fun hideLoading() {
            isLoading = false
        }

        suspend fun displayAlert(
            title: String,
            message: String,
            acceptText: String = "",
            color: Color = accentColor,
            dismiss: () -> Unit = {}
        ) {
            alertTitle = title
            alertMessage = message
            alertAcceptText = acceptText.ifEmpty { getString(Res.string.Accept) }
            alertDismiss = dismiss
            alertColor = color
            alertShow = true
        }

        suspend fun displayConfirm(
            title: String,
            message: String,
            confirmText: String = "",
            cancelText: String = "",
            color: Color = accentColor,
            dismiss: () -> Unit = {},
            accept: () -> Unit
        ) {
            confirmTitle = title
            confirmMessage = message
            confirmAcceptText = confirmText.ifEmpty { getString(Res.string.Accept) }
            confirmCancelText = cancelText.ifEmpty { getString(Res.string.Cancel) }
            confirmColor = color
            confirmDismiss = { dismiss() }
            confirmAccept = { accept() }
            confirmShow = true
        }

        suspend fun displayList(
            title: String,
            subtitle: String,
            placeholder: String = "",
            confirmText: String = "",
            cancelText: String = "",
            options: List<String> = listOf(),
            color: Color = accentColor,
            dismiss: () -> Unit = {},
            accept: (String) -> Unit
        ) {
            listTitle = title
            listSubtitle = subtitle
            listPlaceholder = placeholder.ifEmpty { title }
            listAcceptText = confirmText.ifEmpty { getString(Res.string.Accept) }
            listCancelText = cancelText.ifEmpty { getString(Res.string.Cancel) }
            listOptions = options
            listColor = color
            listAccept = { accept(it) }
            listDismiss = { dismiss() }
            listShow = true
        }

        suspend fun displayPrompt(
            title: String,
            subtitle: String,
            placeholder: String = "",
            confirmText: String = "",
            cancelText: String = "",
            input: String,
            color: Color = accentColor,
            dismiss: () -> Unit = {},
            accept: (String) -> Unit
        ) {
            promptTitle = title
            promptSubtitle = subtitle
            promptPlaceholder = placeholder
            promptAcceptText = confirmText.ifEmpty { getString(Res.string.Accept) }
            promptCancelText = cancelText.ifEmpty { getString(Res.string.Cancel) }
            promptInput = input
            promptColor = color
            promptAccept = { accept(it) }
            promptDismiss = { dismiss() }
            promptShow = true
        }

        suspend fun displayProgress(
            title: String = "",
            cancelText: String = "",
            color: Color = accentColor,
            dismiss: () -> Unit = {},
        ) {
            progressTitle = title.ifEmpty { getString(Res.string.Downloading) }
            progressCancelText = cancelText.ifEmpty { getString(Res.string.Cancel) }
            progressProgress = 0f
            progressDismiss = { dismiss() }
            progressColor = color
            progressShow = true
        }
    }
}