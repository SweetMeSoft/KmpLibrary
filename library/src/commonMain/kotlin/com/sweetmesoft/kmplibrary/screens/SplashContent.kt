package com.sweetmesoft.kmplibrary.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmp_library.library.generated.resources.By
import kmp_library.library.generated.resources.Res
import kmp_library.library.generated.resources.Slogan
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SplashContent(
    logo: DrawableResource,
    waitMillis: Int = 1500,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    background2Color: Color = MaterialTheme.colors.secondary,
    onBackgroundColor: Color = MaterialTheme.colors.onSecondary,
    title: String = stringResource(Res.string.By),
    subtitle: String = stringResource(Res.string.Slogan),
    action: () -> Unit
) {
    var showLogo by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        backgroundColor,
                        background2Color
                    ),
                    radius = 500f,
                    tileMode = TileMode.Clamp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = showLogo,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -it / 2 }),
            ) {
                Image(
                    painter = painterResource(logo),
                    contentDescription = null,
                    modifier = Modifier.size(240.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = onBackgroundColor
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = onBackgroundColor
            )
        }
    }

    LaunchedEffect(Unit) {
        delay(100)
        showLogo = true
        delay(waitMillis.toLong())
        action()
    }
}