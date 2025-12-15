package com.sweetmesoft.kmptestapp.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewModelScope
import com.sweetmesoft.kmpbase.base.BaseViewModel
import com.sweetmesoft.kmpbase.tools.NetworkUtils.post
import com.sweetmesoft.kmpbase.tools.resizeImage
import com.sweetmesoft.kmpbase.tools.toBase64
import com.sweetmesoft.kmptestapp.PhotoProfileRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {
    var state by mutableStateOf(MainState())
        private set

    data class MainState(
        val urlImage: String = "https://storage.googleapis.com/ctc_images/profiles/testcolor.png"
    )

    fun updatePhoto(bitmap: ImageBitmap) {
        val resizedPhoto = resizeImage(bitmap, 500)
        val base64 = resizedPhoto.toBase64()

        viewModelScope.launch {
            post<String>(
                "http://ctcmaintenance-001-site1.btempurl.com/Api/Auth/UpdatePhotoProfileTest",
                PhotoProfileRequest(base64 = base64)
            ).onSuccess {
                val temp = state.urlImage
                updateState(urlImage = "")
                delay(300)
                updateState(urlImage = temp)
            }
        }
    }

    private fun updateState(
        urlImage: String = state.urlImage
    ) {
        state = state.copy(
            urlImage = urlImage
        )
    }
}