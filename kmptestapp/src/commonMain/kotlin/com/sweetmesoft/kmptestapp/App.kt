package com.sweetmesoft.kmptestapp

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.sweetmesoft.kmplibrary.base.BaseViewModel
import com.sweetmesoft.kmptestapp.screens.splash.SplashScreen
import com.sweetmesoft.kmptestapp.theme.MyApplicationTheme

@Composable
fun App() {
    MyApplicationTheme {
        Navigator(screen = SplashScreen()) { navigator ->
            BaseViewModel.navigator = navigator
            SlideTransition(navigator)
        }
    }
}