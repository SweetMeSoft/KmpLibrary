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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.sweetmesoft.kmpbase.objects.IconAction
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(
    title: String,
    iconActions: List<IconAction> = listOf(),
    toolbarColor: Color = MaterialTheme.colorScheme.surface,
    onToolbarColor: Color = MaterialTheme.colorScheme.onSurface,
    navigationIcon: ImageVector = TablerIcons.ArrowBack,
    onNavigationClick: () -> Unit = { BaseViewModel.navigator.pop() }
) {
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
                modifier = Modifier.padding(end = 16.dp)
            ) {
                if (iconActions.any()) {
                    iconActions.forEach { action ->
                        if (action.showIcon) {
                            IconButton(
                                onClick = action.onClick,
                                modifier = Modifier.padding(start = 12.dp).size(28.dp)
                            ) {
                                Icon(
                                    imageVector = action.icon,
                                    contentDescription = null,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }, navigationIcon = {
        if (BaseViewModel.navigator.canPop) {
            IconButton(
                modifier = Modifier.padding(start = 8.dp), onClick = { onNavigationClick() }) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }
        } else {
            null
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = toolbarColor,
        titleContentColor = onToolbarColor,
        navigationIconContentColor = onToolbarColor,
        actionIconContentColor = onToolbarColor
    )
    )
}