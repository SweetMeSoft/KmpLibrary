package com.sweetmesoft.kmplibrary.controls.commonList

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sweetmesoft.kmplibrary.tools.NetworkUtils.get
import com.sweetmesoft.kmplibrary.tools.emptyFunction
import compose.icons.TablerIcons
import compose.icons.tablericons.Plus
import kmplibrary.library.generated.resources.Loading
import kmplibrary.library.generated.resources.Res
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : Any> RemoteGridList(
    modifier: Modifier = Modifier,
    url: String,
    list: List<T> = emptyList(),
    refresh: Boolean = false,
    title: String = "",
    bearer: String = "",
    columns: Int = 2,
    spaceBetween: Dp = 16.dp,
    crossinline itemContent: (@Composable (Int, T) -> Unit),
    crossinline refreshedList: ((List<T>) -> Unit) = {},
    noinline addClick: (() -> Unit) = emptyFunction
) {
    var isLoading by remember { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(refresh) }
    val scope = rememberCoroutineScope()
    rememberPullToRefreshState()
    val refreshAction: () -> Unit = {
        scope.launch {
            isRefreshing = true
            get<List<T>>(url, false, bearer).onSuccess {
                refreshedList(it.obj)
            }.onFailure {}.also {
                isRefreshing = false
            }
        }
    }

    LaunchedEffect(Unit) {
        isLoading = true
        get<List<T>>(url, false, bearer).onSuccess {
            refreshedList(it.obj)
            isLoading = false
        }
    }

    LaunchedEffect(refresh) {
        if (refresh) {
            isLoading = true
            get<List<T>>(url, false, bearer).onSuccess {
                refreshedList(it.obj)
                isLoading = false
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        if (isLoading) {
            androidx.compose.animation.AnimatedVisibility(
                visible = isLoading, enter = fadeIn(), exit = fadeOut()
            ) {
                Column(
                    modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(stringResource(Res.string.Loading) + "...")
                }
            }
        } else {
            if (title.isNotEmpty()) {
                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = title,
                        fontSize = 24.sp,
                    )

                    Spacer(Modifier.weight(1f))

                    if (addClick != emptyFunction) {
                        IconButton(onClick = { addClick() }) {
                            Icon(
                                imageVector = TablerIcons.Plus,
                                contentDescription = "Add list event",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                HorizontalDivider(
                    color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = refreshAction,
                modifier = Modifier.fillMaxSize()
            ) {
                GridBase(modifier, list, columns, spaceBetween, itemContent)
            }
        }
    }
}