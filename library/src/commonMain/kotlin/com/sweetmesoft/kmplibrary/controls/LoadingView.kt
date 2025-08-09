package com.sweetmesoft.kmplibrary.controls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sweetmesoft.kmplibrary.controls.alerts.PopupHandler
import kmplibrary.library.generated.resources.Loading
import kmplibrary.library.generated.resources.Res
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoadingView() {
    AnimatedVisibility(
        visible = PopupHandler.isLoading, enter = fadeIn(), exit = fadeOut()
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f))
                .padding(16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = if (PopupHandler.accentColor == Color.Unspecified) MaterialTheme.colorScheme.primary else PopupHandler.accentColor
                )
                Text(text = stringResource(Res.string.Loading) + "...", color = Color.White)
            }
        }
    }
}