package com.sweetmesoft.kmplibrary.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kmplibrary.library.generated.resources.Nunito_Bold
import kmplibrary.library.generated.resources.Nunito_BoldItalic
import kmplibrary.library.generated.resources.Nunito_ExtraBold
import kmplibrary.library.generated.resources.Nunito_ExtraBoldItalic
import kmplibrary.library.generated.resources.Nunito_ExtraLight
import kmplibrary.library.generated.resources.Nunito_ExtraLightItalic
import kmplibrary.library.generated.resources.Nunito_Italic
import kmplibrary.library.generated.resources.Nunito_Light
import kmplibrary.library.generated.resources.Nunito_LightItalic
import kmplibrary.library.generated.resources.Nunito_Medium
import kmplibrary.library.generated.resources.Nunito_MediumItalic
import kmplibrary.library.generated.resources.Nunito_Regular
import kmplibrary.library.generated.resources.Nunito_SemiBold
import kmplibrary.library.generated.resources.Nunito_SemiBoldItalic
import kmplibrary.library.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun poppinsFontFamily() =
    FontFamily(
        Font(Res.font.Nunito_Bold, weight = FontWeight.Bold),
        Font(
            Res.font.Nunito_BoldItalic,
            weight = FontWeight.Bold,
            style = FontStyle.Italic
        ),
        Font(Res.font.Nunito_ExtraBold, weight = FontWeight.ExtraBold),
        Font(
            Res.font.Nunito_ExtraBoldItalic,
            weight = FontWeight.ExtraBold,
            style = FontStyle.Italic
        ),
        Font(Res.font.Nunito_ExtraLight, weight = FontWeight.ExtraLight),
        Font(
            Res.font.Nunito_ExtraLightItalic,
            weight = FontWeight.ExtraLight,
            style = FontStyle.Italic
        ),
        Font(Res.font.Nunito_Italic, weight = FontWeight.Normal, style = FontStyle.Italic),
        Font(Res.font.Nunito_Light, weight = FontWeight.Light),
        Font(
            Res.font.Nunito_LightItalic,
            weight = FontWeight.Light,
            style = FontStyle.Italic
        ),
        Font(Res.font.Nunito_Medium, weight = FontWeight.Medium),
        Font(
            Res.font.Nunito_MediumItalic,
            weight = FontWeight.Medium,
            style = FontStyle.Italic
        ),
        Font(Res.font.Nunito_Regular, weight = FontWeight.Normal),
        Font(Res.font.Nunito_SemiBold, weight = FontWeight.SemiBold),
        Font(
            Res.font.Nunito_SemiBoldItalic,
            weight = FontWeight.SemiBold,
            style = FontStyle.Italic
        )
    )

@Composable
fun getDefaultTypography() =
    Typography().run {
        val fontFamily = poppinsFontFamily()
        copy(
            displayLarge = displayLarge.copy(fontFamily = fontFamily),
            displayMedium = displayMedium.copy(fontFamily = fontFamily),
            displaySmall = displaySmall.copy(fontFamily = fontFamily),
            headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
            headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
            headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
            titleLarge = titleLarge.copy(fontFamily = fontFamily),
            titleMedium = titleMedium.copy(fontFamily = fontFamily),
            titleSmall = titleSmall.copy(fontFamily = fontFamily),
            labelLarge = labelLarge.copy(fontFamily = fontFamily),
            labelMedium = labelMedium.copy(fontFamily = fontFamily),
            labelSmall = labelSmall.copy(fontFamily = fontFamily),
            bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
            bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
            bodySmall = bodySmall.copy(fontFamily = fontFamily)
        )
    }

@Composable
fun CustomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    lightScheme: ColorScheme = CustomColors.defaultLightScheme(),
    darkScheme: ColorScheme = CustomColors.defaultDarkScheme(),
    typography: Typography = getDefaultTypography(),
    content: @Composable() () -> Unit
) {
    val colors =
        if (darkTheme) {
            darkScheme
        } else {
            lightScheme
        }

    MaterialTheme(colorScheme = colors, content = content, shapes = shapes, typography = typography)
}
