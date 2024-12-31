package com.sweetmesoft.kmplibrary.tools

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
expect fun SetStatusBarColor(color: Color, darkIcons: Boolean)

@Composable
expect fun SetNavigationBarColor(color: Color, darkIcons: Boolean)

val disabledColor = Color(0xFFF5F5F5)
val disabledColorDark = Color(0xFF2C2C2C)
val disabledColorText = Color(0xFFB0B0B0)
val disabledColorTextDark = Color(0xFF5A5A5A)