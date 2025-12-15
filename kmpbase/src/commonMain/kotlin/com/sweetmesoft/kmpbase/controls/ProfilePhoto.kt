package com.sweetmesoft.kmpbase.controls
//import network.chaintech.cmpimagepickncrop.CMPImagePickNCropDialog
//import network.chaintech.cmpimagepickncrop.imagecropper.rememberImageCropper
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sweetmesoft.kmpbase.tools.createHttpClient
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpUrlFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.kamel.image.config.Default
import io.kamel.image.config.LocalKamelConfig
import kmplibrary.kmpbase.generated.resources.Res
import kmplibrary.kmpbase.generated.resources.default_profile
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfilePhoto(
    modifier: Modifier = Modifier,
    urlImage: String,
    radius: Dp = 140.dp,
    invalidateCache: Boolean = false,
    loaderColor: Color = MaterialTheme.colorScheme.primary,
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
    CompositionLocalProvider(LocalKamelConfig provides customKamelConfig) {
        KamelImage(
            {
            asyncPainterResource(data = urlImage)
        },
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = modifier.size(radius).clip(CircleShape).clickable {
                onClick()
            },
            animationSpec = tween(),
            onFailure = {
                Image(
                    painterResource(Res.drawable.default_profile),
                    contentDescription = "Profile",
                    modifier = Modifier.size(radius).clip(CircleShape).clickable { onClick() },
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