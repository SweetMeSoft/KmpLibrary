package com.sweetmesoft.kmptestapp.screens.colors

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sweetmesoft.kmpbase.base.BaseViewModel

class ColorsViewModel() : BaseViewModel() {

    var state by mutableStateOf(ColorsState())
        private set

    data class ColorsState(
        val colors: List<ColorItem> = listOf()
    )

    init {
        println("ColorsViewModel")
    }
}
