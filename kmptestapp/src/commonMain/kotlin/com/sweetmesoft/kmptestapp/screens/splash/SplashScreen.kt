package com.sweetmesoft.kmptestapp.screens.splash

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmplibrary.base.BaseScreen
import com.sweetmesoft.kmplibrary.base.BaseViewModel.Companion.navigator
import com.sweetmesoft.kmplibrary.screens.SplashContent
import com.sweetmesoft.kmptestapp.screens.main.MainScreen
import kmplibrary.kmptestapp.generated.resources.Res
import kmplibrary.kmptestapp.generated.resources.sweetmesoft_logo_sq_transparent

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        BaseScreen(
            toolbarColor = MaterialTheme.colors.primary,
            toolbarIconsLight = false
        ) {
            SplashContent(
                logo = Res.drawable.sweetmesoft_logo_sq_transparent,
                backgroundColor = MaterialTheme.colors.primaryVariant,
                background2Color = MaterialTheme.colors.primary,
                action = { navigator.replace(MainScreen()) }
            )
        }
    }
}