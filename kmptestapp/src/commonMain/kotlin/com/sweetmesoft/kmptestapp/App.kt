package com.sweetmesoft.kmptestapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.sweetmesoft.kmpbase.base.BaseViewModel
import com.sweetmesoft.kmpbase.theme.CustomTheme
import com.sweetmesoft.kmptestapp.screens.splash.SplashScreen

@Composable
fun App() {
    CustomTheme(primaryColor = Color(0xFF003b61), shapes = "circle") {
        Navigator(screen = SplashScreen()) { navigator ->
            BaseViewModel.navigator = navigator
            SlideTransition(navigator)
        }
    }
}