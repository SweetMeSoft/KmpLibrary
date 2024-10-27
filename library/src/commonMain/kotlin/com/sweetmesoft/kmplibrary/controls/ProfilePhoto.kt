package com.sweetmesoft.kmplibrary.controls

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
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
    onClick: () -> Unit = {}
) {
//    val imageCropper = rememberImageCropper()
    var selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }
    var openImagePicker by remember { mutableStateOf(value = false) }

    when (val resource =
        asyncPainterResource(urlImage)) {
        is Resource.Loading -> {
            Box(
                modifier = modifier
                    .size(radius)
                    .clip(CircleShape)
                    .clickable { openImagePicker = true },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            Image(
                resource.value,
                contentDescription = "Profile",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(radius)
                    .clip(CircleShape)
                    .clickable {
                        onClick()
                        openImagePicker = true
                    }
            )
        }

        is Resource.Failure -> {
            Image(
                painterResource(Res.drawable.default_profile),
                contentDescription = "Profile",
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .size(radius)
                    .clip(CircleShape)
                    .clickable { openImagePicker = true },
            )
        }
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
