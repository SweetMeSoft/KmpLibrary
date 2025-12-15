package com.sweetmesoft.kmptestapp

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.sweetmesoft.kmplibrary.base.BaseViewModel
import com.sweetmesoft.kmplibrary.theme.CustomTheme
import com.sweetmesoft.kmptestapp.screens.splash.SplashScreen

@Composable
fun App() {
    CustomTheme(
        darkScheme = darkColorScheme(
            primary = Color(0xFF003b61),
            secondary = Color(0xFF0060bf),
            onPrimary = Color(0xFFFFFFFF)
        )
    ) {
        Navigator(screen = SplashScreen()) { navigator ->
            BaseViewModel.navigator = navigator
            SlideTransition(navigator)
        }
    }
}