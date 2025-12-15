package com.sweetmesoft.kmptestapp.screens.about

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpbase.base.BaseScreen
import com.sweetmesoft.kmpbase.screens.AboutContent
import kmplibrary.kmptestapp.generated.resources.sweetmesoft_logo_ci_color

class AboutScreen : Screen {
    @Composable
    override fun Content() {
        BaseScreen(showTop = true) {
            AboutContent(
                logo = kmplibrary.kmptestapp.generated.resources.Res.drawable.sweetmesoft_logo_ci_color,
                appName = "KMP TestApp",
                appId = "com.sweetmesoft.kmptestapp"
            )
        }
    }
}