package com.sweetmesoft.kmplibrary.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sweetmesoft.kmplibrary.controls.SettingsItem
import com.sweetmesoft.kmplibrary.tools.PlatformType
import com.sweetmesoft.kmplibrary.tools.getAppVersion
import com.sweetmesoft.kmplibrary.tools.getPlatform
import com.sweetmesoft.kmplibrary.tools.openAppStore
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.AppStore
import compose.icons.fontawesomeicons.brands.GooglePlay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
private fun AboutContent(logo: DrawableResource, appName: String, appId: String) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = painterResource(logo),
            contentDescription = null,
            modifier = Modifier
                .size(144.dp)
                .padding(bottom = 8.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Gracias por usar $appName. Esperamos que tu experiencia sea excelente y que encuentres lo que necesitas. Recuerda calificarnos.",
            fontSize = 16.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 48.dp)
        )

        SettingsItem(
            icon = if(getPlatform() == PlatformType.ANDROID) FontAwesomeIcons.Brands.GooglePlay else FontAwesomeIcons.Brands.AppStore,
            title = "Califícanos",
            description = "Si has disfrutado de $appName nos alegraría que nos califiques"
        ) {
            openAppStore(appId)
        }

        SettingsItem(title = "Versión", description = getAppVersion())
    }
}