package com.sweetmesoft.kmplibrary.controls

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sweetmesoft.kmplibrary.tools.createHttpClient
import io.kamel.core.Resource
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpUrlFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.kamel.image.config.Default
import io.kamel.image.config.LocalKamelConfig
import kmp_library.library.generated.resources.Res
import kmp_library.library.generated.resources.default_profile
//import network.chaintech.cmpimagepickncrop.CMPImagePickNCropDialog
//import network.chaintech.cmpimagepickncrop.imagecropper.rememberImageCropper
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfilePhoto(
    modifier: Modifier = Modifier,
    urlImage: String,
    radius: Dp = 140.dp,
    invalidateCache: Boolean = false,
    loaderColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit = {}
) {
    val customKamelConfig = KamelConfig {
        takeFrom(KamelConfig.Default)
        if (invalidateCache) {
            imageBitmapCacheSize = 1
        }
        httpUrlFetcher(createHttpClient())
    }
//    val imageCropper = rememberImageCropper()
    var selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }
    var openImagePicker by remember { mutableStateOf(value = false) }

    CompositionLocalProvider(LocalKamelConfig provides customKamelConfig) {
        KamelImage(
            {
                asyncPainterResource(data = urlImage)
            },
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(radius)
                .clip(CircleShape)
                .clickable {
                    onClick()
                },
            animationSpec = tween(),
            onFailure = {
                Image(
                    painterResource(Res.drawable.default_profile),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(radius)
                        .clip(CircleShape)
                        .clickable { onClick() },
                )
            },
            onLoading = {
                Box(
                    modifier = modifier
                        .size(radius)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = loaderColor)
                }
            }
        )
    }

//    CMPImagePickNCropDialog(
//        imageCropper = imageCropper,
//        openImagePicker = openImagePicker,
//        imagePickerDialogHandler = {
//            openImagePicker = it
//        },
//        selectedImageCallback = {
//            updatePhoto(it)
//            selectedImage = it
//        }
//    )
}