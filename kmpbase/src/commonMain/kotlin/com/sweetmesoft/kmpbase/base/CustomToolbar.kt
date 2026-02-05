package com.sweetmesoft.kmpbase.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.sweetmesoft.kmpbase.controls.MoreControl
import com.sweetmesoft.kmpbase.objects.IconAction
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(
    title: String,
    iconActions: List<IconAction> = listOf(),
    toolbarColor: Color = MaterialTheme.colorScheme.surface,
    onToolbarColor: Color = MaterialTheme.colorScheme.onSurface,
    showNavigation: Boolean = false,
    navigationIcon: ImageVector = TablerIcons.Outlined.ArrowBack,
    onNavigationClick: () -> Unit = { BaseViewModel.navigator.pop() }
) {
    val visibleActions = remember(iconActions) { iconActions.filter { !it.showStacked } }
    val overflowActions = remember(iconActions) { iconActions.filter { it.showStacked } }

    TopAppBar(
        title = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(title, maxLines = 1)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                if (visibleActions.isNotEmpty()) {
                    visibleActions.forEach { action ->
                        IconButton(
                            onClick = action.onClick,
                            modifier = Modifier.padding(start = 12.dp).size(28.dp)
                        ) {
                            action.icon?.let {
                                Icon(
                                    imageVector = it,
                                    contentDescription = action.text,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                    }
                }

                if (overflowActions.isNotEmpty()) {
                    MoreControl(options = overflowActions)
                }
            }
        }
    }, navigationIcon = {
        if (BaseViewModel.navigator.canPop || showNavigation) {
            IconButton(
                modifier = Modifier.padding(start = 8.dp), onClick = { onNavigationClick() }) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = toolbarColor,
        titleContentColor = onToolbarColor,
        navigationIconContentColor = onToolbarColor,
        actionIconContentColor = onToolbarColor
    )
    )
}
