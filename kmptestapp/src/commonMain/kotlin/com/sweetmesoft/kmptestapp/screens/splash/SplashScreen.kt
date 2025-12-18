package com.sweetmesoft.kmptestapp.screens.splash

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpbase.base.BaseScreen
import com.sweetmesoft.kmpbase.base.BaseViewModel.Companion.navigator
import com.sweetmesoft.kmpbase.screens.SplashContent
import com.sweetmesoft.kmptestapp.screens.main.MainScreen
import kmplibrary.kmptestapp.generated.resources.Res
import kmplibrary.kmptestapp.generated.resources.sweetmesoft_logo_sq_transparent

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        BaseScreen(
            toolbarColor = MaterialTheme.colorScheme.primary, toolbarIconsLight = false
        ) {
            SplashContent(
                logo = Res.drawable.sweetmesoft_logo_sq_transparent,
                backgroundColor = MaterialTheme.colorScheme.primary,
                background2Color = MaterialTheme.colorScheme.primary,
                onBackgroundColor = MaterialTheme.colorScheme.onPrimary,
                action = { navigator.replace(MainScreen()) })
        }
    }
}