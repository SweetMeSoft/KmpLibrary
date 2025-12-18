package com.sweetmesoft.kmptestapp.screens.colors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmpbase.base.BaseScreen
import com.sweetmesoft.kmpbase.controls.commonList.LocalGridList

data class ColorItem(val name: String, val color: Color, val onColor: Color? = null)

class ColorsScreen : Screen {
    @Composable
    override fun Content() {
        remember { ColorsViewModel() }
        val colorScheme = getColorScheme()
        val colors = remember { defineList(colorScheme) }

        BaseScreen(
            title = "Theme Colors"
        ) {
            Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
                LocalGridList(
                    list = colors, modifier = Modifier.fillMaxSize(), spaceBetween = 12.dp
                ) { _, item -> ColorCard(item) }
            }
        }
    }

    @Composable
    private fun getColorScheme(): ColorScheme {
        return MaterialTheme.colorScheme
    }

    @Composable
    fun ColorCard(item: ColorItem) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(item.color, shape = RoundedCornerShape(8.dp)).padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                if (item.onColor != null) {
                    Column {
                        Text(
                            text = item.name,
                            color = item.onColor,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = getHexCode(item.color),
                            style = MaterialTheme.typography.bodyMedium,
                            color = item.onColor,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    private fun defineList(colorScheme: ColorScheme): List<ColorItem> {
        return listOf(
            ColorItem("Primary", colorScheme.primary, colorScheme.onPrimary),
            ColorItem("On Primary", colorScheme.onPrimary, colorScheme.primary),
            ColorItem(
                "Primary Container", colorScheme.primaryContainer, colorScheme.onPrimaryContainer
            ),
            ColorItem(
                "On Primary Container", colorScheme.onPrimaryContainer, colorScheme.primaryContainer
            ),
            ColorItem("Inverse Primary", colorScheme.inversePrimary, colorScheme.primary),
            ColorItem("Secondary", colorScheme.secondary, colorScheme.onSecondary),
            ColorItem("On Secondary", colorScheme.onSecondary, colorScheme.secondary),
            ColorItem(
                "Secondary Container",
                colorScheme.secondaryContainer,
                colorScheme.onSecondaryContainer
            ),
            ColorItem(
                "On Secondary Container",
                colorScheme.onSecondaryContainer,
                colorScheme.secondaryContainer
            ),
            ColorItem("Tertiary", colorScheme.tertiary, colorScheme.onTertiary),
            ColorItem("On Tertiary", colorScheme.onTertiary, colorScheme.tertiary),
            ColorItem(
                "Tertiary Container", colorScheme.tertiaryContainer, colorScheme.onTertiaryContainer
            ),
            ColorItem(
                "On Tertiary Container",
                colorScheme.onTertiaryContainer,
                colorScheme.tertiaryContainer
            ),
            ColorItem("Background", colorScheme.background, colorScheme.onBackground),
            ColorItem("On Background", colorScheme.onBackground, colorScheme.background),
            ColorItem("Surface", colorScheme.surface, colorScheme.onSurface),
            ColorItem("On Surface", colorScheme.onSurface, colorScheme.surface),
            ColorItem(
                "Surface Variant", colorScheme.surfaceVariant, colorScheme.onSurfaceVariant
            ),
            ColorItem(
                "On Surface Variant", colorScheme.onSurfaceVariant, colorScheme.surfaceVariant
            ),
            ColorItem("Surface Tint", colorScheme.surfaceTint, colorScheme.onPrimary),
            ColorItem(
                "Inverse Surface", colorScheme.inverseSurface, colorScheme.inverseOnSurface
            ),
            ColorItem(
                "Inverse On Surface", colorScheme.inverseOnSurface, colorScheme.inverseSurface
            ),
            ColorItem("Error", colorScheme.error, colorScheme.onError),
            ColorItem("On Error", colorScheme.onError, colorScheme.error),
            ColorItem(
                "Error Container", colorScheme.errorContainer, colorScheme.onErrorContainer
            ),
            ColorItem(
                "On Error Container", colorScheme.onErrorContainer, colorScheme.errorContainer
            ),
            ColorItem("Outline", colorScheme.outline, colorScheme.onPrimary),
            ColorItem("Outline Variant", colorScheme.outlineVariant, colorScheme.onPrimary),
            ColorItem("Scrim", colorScheme.scrim, Color.White),
            ColorItem("Surface Bright", colorScheme.surfaceBright, colorScheme.onSurface),
            ColorItem(
                "Surface Container Highest",
                colorScheme.surfaceContainerHighest,
                colorScheme.onSurface
            ),
            ColorItem(
                "Surface Container High", colorScheme.surfaceContainerHigh, colorScheme.onSurface
            ),
            ColorItem("Surface Container", colorScheme.surfaceContainer, colorScheme.onSurface),
            ColorItem(
                "Surface Container Low", colorScheme.surfaceContainerLow, colorScheme.onSurface
            ),
            ColorItem(
                "Surface Container Lowest",
                colorScheme.surfaceContainerLowest,
                colorScheme.onSurface
            ),
            ColorItem("Surface Dim", colorScheme.surfaceDim, colorScheme.onSurface),

            // Fixed colors
            ColorItem("Primary Fixed", colorScheme.primaryFixed, colorScheme.onPrimaryFixed),
            ColorItem("On Primary Fixed", colorScheme.onPrimaryFixed, colorScheme.primaryFixed),
            ColorItem(
                "Primary Fixed Dim", colorScheme.primaryFixedDim, colorScheme.onPrimaryFixedVariant
            ),
            ColorItem(
                "On Primary Fixed Variant",
                colorScheme.onPrimaryFixedVariant,
                colorScheme.primaryFixedDim
            ),
            ColorItem(
                "Secondary Fixed", colorScheme.secondaryFixed, colorScheme.onSecondaryFixed
            ),
            ColorItem(
                "On Secondary Fixed", colorScheme.onSecondaryFixed, colorScheme.secondaryFixed
            ),
            ColorItem(
                "Secondary Fixed Dim",
                colorScheme.secondaryFixedDim,
                colorScheme.onSecondaryFixedVariant
            ),
            ColorItem(
                "On Secondary Fixed Variant",
                colorScheme.onSecondaryFixedVariant,
                colorScheme.secondaryFixedDim
            ),
            ColorItem("Tertiary Fixed", colorScheme.tertiaryFixed, colorScheme.onTertiaryFixed),
            ColorItem(
                "On Tertiary Fixed", colorScheme.onTertiaryFixed, colorScheme.tertiaryFixed
            ),
            ColorItem(
                "Tertiary Fixed Dim",
                colorScheme.tertiaryFixedDim,
                colorScheme.onTertiaryFixedVariant
            ),
            ColorItem(
                "On Tertiary Fixed Variant",
                colorScheme.onTertiaryFixedVariant,
                colorScheme.tertiaryFixedDim
            ),
        )
    }

    private fun getHexCode(color: Color): String {
        val alpha = (color.alpha * 255).toInt()
        val red = (color.red * 255).toInt()
        val green = (color.green * 255).toInt()
        val blue = (color.blue * 255).toInt()

        fun Int.toHex2(): String {
            val hex = this.toString(16).uppercase()
            return if (hex.length == 1) "0$hex" else hex
        }

        return "#${alpha.toHex2()}${red.toHex2()}${green.toHex2()}${blue.toHex2()}"
    }
}
