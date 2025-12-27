package com.sweetmesoft.kmptestapp.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.sweetmesoft.kmpbase.base.BaseTab
import com.sweetmesoft.kmpbase.base.BaseTabOptions
import com.sweetmesoft.kmpbase.base.defaultBaseTabOptions
import com.sweetmesoft.kmptestapp.components.DefaultPlaceHolder
import compose.icons.TablerIcons
import compose.icons.tablericons.Settings

class SettingsTab : BaseTab {
    override val baseOptions: BaseTabOptions
        @Composable get() = defaultBaseTabOptions(
            title = "Configuraciones", icon = rememberVectorPainter(image = TablerIcons.Settings)
        )

    @Composable
    override fun Content() {
        DefaultPlaceHolder("Settings Tab Content")
    }
}