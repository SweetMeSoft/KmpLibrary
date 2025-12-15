package com.sweetmesoft.kmpbase.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sweetmesoft.kmpbase.controls.SettingsItem
import com.sweetmesoft.kmpbase.tools.PlatformType
import com.sweetmesoft.kmpbase.tools.getAppVersion
import com.sweetmesoft.kmpbase.tools.getPlatform
import com.sweetmesoft.kmpbase.tools.openAppStore
import com.sweetmesoft.kmpbase.tools.openUrl
import compose.icons.TablerIcons
import compose.icons.tablericons.BrandAppstore
import compose.icons.tablericons.BrandGooglePlay
import compose.icons.tablericons.Versions
import kmplibrary.kmpbase.generated.resources.By
import kmplibrary.kmpbase.generated.resources.RateUs
import kmplibrary.kmpbase.generated.resources.RateUsMessage
import kmplibrary.kmpbase.generated.resources.Res
import kmplibrary.kmpbase.generated.resources.Slogan
import kmplibrary.kmpbase.generated.resources.ThanksMessage
import kmplibrary.kmpbase.generated.resources.Version
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AboutContent(logo: DrawableResource, appName: String, appId: String) {
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(logo),
            contentDescription = null,
            modifier = Modifier.size(144.dp).padding(bottom = 8.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = stringResource(Res.string.ThanksMessage, appName),
            fontSize = 16.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 48.dp)
        )

        SettingsItem(
            icon = if (getPlatform() == PlatformType.ANDROID) TablerIcons.BrandGooglePlay else TablerIcons.BrandAppstore,
            title = stringResource(Res.string.RateUs),
            description = stringResource(Res.string.RateUsMessage, appName),
        ) {
            openAppStore(appId)
        }

        SettingsItem(
            title = stringResource(Res.string.Version),
            description = getAppVersion(),
            icon = TablerIcons.Versions
        )

        Spacer(modifier = Modifier.weight(1f))


        Text(
            modifier = Modifier.clickable {
                openUrl("https://www.sweetmesoft.com")
            },
            text = stringResource(Res.string.By),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
        Text(
            modifier = Modifier.padding(bottom = 32.dp),
            text = stringResource(Res.string.Slogan),
            fontSize = 14.sp
        )
    }
}