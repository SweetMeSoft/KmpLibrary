package com.sweetmesoft.kmplibrary.tools

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UINavigationBar
import platform.UIKit.UIStatusBarStyle
import platform.UIKit.UIView
import platform.UIKit.setStatusBarStyle
import platform.UIKit.statusBarManager

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun SetStatusBarColor(color: Color, darkIcons: Boolean) {
    val uiColor = UIColor(color)

    if (UIApplication.sharedApplication.keyWindow != null) {
        val statusBar =
            UIView(frame = UIApplication.sharedApplication.keyWindow?.windowScene?.statusBarManager!!.statusBarFrame)
        statusBar.backgroundColor = uiColor
        UIApplication.sharedApplication.keyWindow!!.addSubview(statusBar)

        val style = if (darkIcons) {
            UIStatusBarStyle.MIN_VALUE
        } else {
            UIStatusBarStyle.MAX_VALUE
        }

        UIApplication.sharedApplication.setStatusBarStyle(style)
    }
}

@Composable
actual fun SetNavigationBarColor(
    color: Color,
    darkIcons: Boolean
) {
    UINavigationBar.appearance().barTintColor = UIColor(color)
}

private fun UIColor(color: Color): UIColor {
    return UIColor(
        red = color.red.toDouble(),
        green = color.green.toDouble(),
        blue = color.blue.toDouble(),
        alpha = color.alpha.toDouble()
    )
}