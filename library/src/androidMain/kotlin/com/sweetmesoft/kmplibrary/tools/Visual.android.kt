package com.sweetmesoft.kmplibrary.tools

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat

@Composable
actual fun SetStatusBarColor(color: Color, darkIcons: Boolean) {
    val activity = LocalView.current.context as Activity
    val window = activity.window
    window.statusBarColor = color.toArgb()
    ViewCompat.getWindowInsetsController(window.decorView)?.isAppearanceLightStatusBars = darkIcons
}

@Composable
actual fun SetNavigationBarColor(color: Color, darkIcons: Boolean) {
    val activity = LocalView.current.context as Activity
    val window = activity.window
    window.navigationBarColor = color.toArgb()
    WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars =
        darkIcons
}
