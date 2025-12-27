package com.sweetmesoft.kmptestapp.tabs

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.sweetmesoft.kmpbase.base.BaseTab
import com.sweetmesoft.kmpbase.base.BaseTabOptions
import com.sweetmesoft.kmpbase.base.defaultBaseTabOptions
import com.sweetmesoft.kmptestapp.components.DefaultPlaceHolder
import compose.icons.TablerIcons
import compose.icons.tablericons.Star

class FavoritesTab : BaseTab {
    override val baseOptions: BaseTabOptions
        @Composable get() = defaultBaseTabOptions(
            title = "Favoritos",
            showTop = true,
            icon = rememberVectorPainter(image = TablerIcons.Star),
            toolbarColor = MaterialTheme.colorScheme.primary,
            onToolbarColor = MaterialTheme.colorScheme.onPrimary,
            statusDarkIcons = isSystemInDarkTheme()
        )

    @Composable
    override fun Content() {
        DefaultPlaceHolder("Favorites Tab Content")
    }
}
