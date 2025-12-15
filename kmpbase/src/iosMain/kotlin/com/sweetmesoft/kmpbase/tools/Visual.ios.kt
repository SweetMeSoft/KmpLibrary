package com.sweetmesoft.kmpbase.tools

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

    if (UIApplication.sharedApplication.keyWindow != null) {
        val statusBar =
            UIView(frame = UIApplication.sharedApplication.keyWindow?.windowScene?.statusBarManager!!.statusBarFrame)
        statusBar.backgroundColor = color.toUIColor()
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
    color: Color, darkIcons: Boolean
) {
    UINavigationBar.appearance().barTintColor = color.toUIColor()
}

fun Color.toUIColor(): UIColor {
    return UIColor(
        red = this.red.toDouble(),
        green = this.green.toDouble(),
        blue = this.blue.toDouble(),
        alpha = this.alpha.toDouble()
    )
}