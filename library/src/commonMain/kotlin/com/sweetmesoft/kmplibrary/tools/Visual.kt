package com.sweetmesoft.kmplibrary.tools

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
expect fun SetStatusBarColor(color: Color, darkIcons: Boolean)

@Composable
expect fun SetNavigationBarColor(color: Color, darkIcons: Boolean)