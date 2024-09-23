package tools

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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

fun UIColor(color: Color): UIColor {
    return UIColor(
        red = color.red.toDouble(),
        green = color.green.toDouble(),
        blue = color.blue.toDouble(),
        alpha = color.alpha.toDouble()
    )
}