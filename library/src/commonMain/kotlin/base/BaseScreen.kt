package base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import controls.LoadingView
import controls.alerts.AlertConfirm
import controls.alerts.AlertList
import controls.alerts.AlertPrompt
import controls.alerts.AlertView
import controls.alerts.PopupHandler
import objects.IconAction
import tools.SetNavigationBarColor
import tools.SetStatusBarColor
import tools.emptyFunction

@Composable
fun BaseScreen(
    title: String = "",
    showTop: Boolean = false,
    modifier: Modifier = Modifier,
    fabAction: () -> Unit = emptyFunction,
    fabIcon: ImageVector = Icons.Default.Check,
    infiniteStyle: Boolean = true,
    iconActions: List<IconAction> = listOf(),
    content: @Composable () -> Unit
) {
    var toolbarColor = MaterialTheme.colors.background
    if (infiniteStyle) {
        SetStatusBarColor(MaterialTheme.colors.background, MaterialTheme.colors.isLight)
        SetNavigationBarColor(MaterialTheme.colors.background, MaterialTheme.colors.isLight)
    } else {
        toolbarColor =
            if (MaterialTheme.colors.isLight) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        SetStatusBarColor(toolbarColor, false)
    }
    ScreenContent(
        modifier,
        title,
        showTop,
        fabAction,
        fabIcon,
        toolbarColor,
        iconActions
    ) {
        content()
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    title: String,
    showTop: Boolean,
    fabAction: () -> Unit,
    fabIcon: ImageVector,
    toolbarColor: Color,
    iconActions: List<IconAction> = listOf(),
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        topBar = {
            if (title.isNotEmpty() || showTop) {
                TopAppBar(
                    backgroundColor = toolbarColor,
                    elevation = 0.dp,
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(title)
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
                                if (iconActions.any()) {
                                    iconActions.forEach { action ->
                                        if (action.showIcon) {
                                            IconButton(
                                                onClick = action.onClick,
                                                modifier = Modifier.padding(start = 12.dp)
                                                    .size(28.dp)
                                            ) {
                                                Icon(
                                                    imageVector = action.icon,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(4.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    navigationIcon = {
                        if (BaseViewModel.navigator.canPop) {
                            IconButton(
                                modifier = Modifier
                                    .padding(start = 8.dp),
                                onClick = { BaseViewModel.navigator.pop() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (fabAction != emptyFunction) {
                FloatingActionButton(onClick = fabAction) {
                    Icon(
                        imageVector = fabIcon,
                        contentDescription = "fabIcon",
                        tint = Color.DarkGray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    ) {
        content()
        AlertView(
            title = PopupHandler.alertTitle,
            message = PopupHandler.alertMessage,
            dismiss = PopupHandler.alertDismiss
        )

        AlertConfirm(
            title = PopupHandler.confirmTitle,
            message = PopupHandler.confirmMessage,
            confirmText = PopupHandler.confirmAcceptText,
            cancelText = PopupHandler.confirmCancelText,
            dismiss = PopupHandler.confirmDismiss,
        ) {
            PopupHandler.confirmAccept()
        }

        AlertList(
            title = PopupHandler.listTitle,
            message = PopupHandler.listMessage,
            options = PopupHandler.listOptions,
            show = PopupHandler.listShow,
            confirmText = PopupHandler.confirmAcceptText,
            cancelText = PopupHandler.confirmCancelText,
            dismiss = PopupHandler.listDismiss
        ) {
            PopupHandler.listAccept(it)
        }

        AlertPrompt(
            title = PopupHandler.promptTitle,
            subtitle = PopupHandler.promptSubtitle,
            input = PopupHandler.promptInput,
            confirmText = PopupHandler.confirmAcceptText,
            dismiss = PopupHandler.promptDismiss
        ) {
            PopupHandler.promptAccept(it)
        }
    }

    LoadingView(PopupHandler.isLoading)
}