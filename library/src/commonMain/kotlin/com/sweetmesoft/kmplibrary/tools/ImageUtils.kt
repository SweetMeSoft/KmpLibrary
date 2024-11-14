package com.sweetmesoft.kmplibrary.tools

import androidx.compose.ui.graphics.ImageBitmap

expect fun resizeImage(imageBitmap: ImageBitmap, maxSize: Int): ImageBitmap

expect fun imageToBase64(imageBitmap: ImageBitmap): String