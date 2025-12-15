package com.sweetmesoft.kmptestapp.screens.controls

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sweetmesoft.kmpbase.base.BaseViewModel

class ControlsViewModel : BaseViewModel() {
    var searchQuery by mutableStateOf("")
    var password by mutableStateOf("")
    var clickableText by mutableStateOf("Click Me!")
}
