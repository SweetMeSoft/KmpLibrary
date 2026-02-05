package com.sweetmesoft.kmptestapp.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.sweetmesoft.kmpbase.base.BaseTab
import com.sweetmesoft.kmpbase.base.BaseTabOptions
import com.sweetmesoft.kmpbase.base.defaultBaseTabOptions
import com.sweetmesoft.kmptestapp.components.DefaultPlaceHolder
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.Home

class HomeTab : BaseTab {
    override val baseOptions: BaseTabOptions
        @Composable get() = defaultBaseTabOptions(
            title = "Inicio", icon = rememberVectorPainter(image = TablerIcons.Outlined.Home)
        )

    @Composable
    override fun Content() {
        DefaultPlaceHolder("Home Tab Content")
    }
}
