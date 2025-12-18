package com.sweetmesoft.kmptestapp.screens.drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.sweetmesoft.kmpbase.base.BaseTab
import com.sweetmesoft.kmpbase.base.BaseTabOptions
import com.sweetmesoft.kmpbase.base.defaultBaseTabOptions
import compose.icons.TablerIcons
import compose.icons.tablericons.Settings

class SettingsTab : BaseTab {
    override val baseOptions: BaseTabOptions
        @Composable get() = defaultBaseTabOptions(
            title = "Configuración", icon = rememberVectorPainter(image = TablerIcons.Settings)
        )

    @Composable
    override fun Content() {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Settings Tab Content")
        }
    }
}
