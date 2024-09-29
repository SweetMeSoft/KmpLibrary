package controls.alerts

class PopupHandler {
    companion object {
        var isLoading: Boolean = false
        internal var alertShow: Boolean = false
        internal var alertTitle: String = ""
        internal var alertMessage: String = ""
        internal var alertDismiss: () -> Unit = {}

        fun displayAlert(title: String, message: String, dismiss: () -> Unit = {}) {
            alertTitle = title
            alertMessage = message
            alertDismiss = dismiss
            alertShow = true
        }
    }
}