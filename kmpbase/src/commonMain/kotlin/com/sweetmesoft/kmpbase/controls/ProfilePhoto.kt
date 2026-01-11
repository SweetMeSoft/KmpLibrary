package com.sweetmesoft.kmpbase.controls

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sweetmesoft.kmpbase.controls.alerts.BaseDialog
import com.sweetmesoft.kmpbase.tools.createHttpClient
import com.sweetmesoft.kmpbase.tools.toImageBitmap
import io.github.ismoy.imagepickerkmp.domain.config.ImagePickerConfig
import io.github.ismoy.imagepickerkmp.domain.extensions.loadBytes
import io.github.ismoy.imagepickerkmp.presentation.ui.components.GalleryPickerLauncher
import io.github.ismoy.imagepickerkmp.presentation.ui.components.ImagePickerLauncher
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpUrlFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.kamel.image.config.Default
import io.kamel.image.config.LocalKamelConfig
import kmplibrary.kmpbase.generated.resources.Camera
import kmplibrary.kmpbase.generated.resources.Gallery
import kmplibrary.kmpbase.generated.resources.Res
import kmplibrary.kmpbase.generated.resources.SelectSource
import kmplibrary.kmpbase.generated.resources.SelectSourceDescription
import kmplibrary.kmpbase.generated.resources.default_profile
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfilePhoto(
    modifier: Modifier = Modifier,
    urlImage: String,
    radius: Dp = 140.dp,
    allowCamera: Boolean = false,
    allowGallery: Boolean = false,
    invalidateCache: Boolean = false,
    loaderColor: Color = MaterialTheme.colorScheme.primary,
    onPhotoSelected: (ByteArray) -> Unit = {},
    onClick: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var showCamera by remember { mutableStateOf(false) }
    var showGallery by remember { mutableStateOf(false) }
    var showSelectionDialog by remember { mutableStateOf(false) }
    var localImageBytes by remember { mutableStateOf<ByteArray?>(null) }

    val customKamelConfig = KamelConfig {
        takeFrom(KamelConfig.Default)
        if (invalidateCache) {
            imageBitmapCacheSize = 1
        }
        httpUrlFetcher(createHttpClient())
    }

    CompositionLocalProvider(LocalKamelConfig provides customKamelConfig) {
        Box {
            if (localImageBytes != null) {
                localImageBytes?.toImageBitmap()?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                        modifier = modifier.size(radius)
                            .clip(shape = MaterialTheme.shapes.extraLarge).clickable {
                                if (allowCamera && allowGallery) {
                                    showSelectionDialog = true
                                } else if (allowCamera) {
                                    showCamera = true
                                } else if (allowGallery) {
                                    showGallery = true
                                } else {
                                    onClick()
                                }
                            })
                }
            } else {
                KamelImage(
                    {
                        asyncPainterResource(data = urlImage)
                    },
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = modifier.size(radius).clip(shape = MaterialTheme.shapes.extraLarge)
                        .clickable {
                            if (allowCamera && allowGallery) {
                                showSelectionDialog = true
                            } else if (allowCamera) {
                                showCamera = true
                            } else if (allowGallery) {
                                showGallery = true
                            } else {
                                onClick()
                            }
                        },
                    animationSpec = tween(),
                    onFailure = {
                        Image(
                            painterResource(Res.drawable.default_profile),
                            contentDescription = "Profile",
                            modifier = Modifier.size(radius).clip(CircleShape).clickable {
                                if (allowCamera && allowGallery) {
                                    showSelectionDialog = true
                                } else if (allowCamera) {
                                    showCamera = true
                                } else if (allowGallery) {
                                    showGallery = true
                                } else {
                                    onClick()
                                }
                            },
                        )
                    },
                    onLoading = {
                        Box(
                            modifier = modifier.size(radius).clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = loaderColor)
                        }
                    })
            }

            if (showCamera) {
                ImagePickerLauncher(
                    config = ImagePickerConfig(
                        onPhotoCaptured = { result ->
                            showCamera = false
                            result.let {
                                scope.launch {
                                    val bytes = it.loadBytes()
                                    localImageBytes = bytes
                                    onPhotoSelected(bytes)
                                }
                            }
                        },
                        onError = { showCamera = false },
                        onDismiss = { showCamera = false },
                        enableCrop = true
                    )
                )
            }

            if (showGallery) {
                GalleryPickerLauncher(
                    enableCrop = true,
                    allowMultiple = false,
                    onPhotosSelected = { photos ->
                        showGallery = false
                        photos.firstOrNull()?.let {
                            scope.launch {
                                val bytes = it.loadBytes()
                                localImageBytes = bytes
                                onPhotoSelected(bytes)
                            }
                        }
                    },
                    onError = { showGallery = false },
                    onDismiss = { showGallery = false })
            }

            if (showSelectionDialog) {
                BaseDialog(
                    acceptText = stringResource(Res.string.Camera),
                    cancelText = stringResource(Res.string.Gallery),
                    color = MaterialTheme.colorScheme.primary,
                    onAccept = {
                        showSelectionDialog = false
                        showCamera = true
                    },
                    onDismiss = {
                        showSelectionDialog = false
                        showGallery = true
                    }) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.SelectSource),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = stringResource(Res.string.SelectSourceDescription),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        }
    }
}