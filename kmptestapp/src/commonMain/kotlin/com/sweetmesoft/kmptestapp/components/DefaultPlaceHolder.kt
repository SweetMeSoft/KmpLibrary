package com.sweetmesoft.kmptestapp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sweetmesoft.kmpbase.base.BaseViewModel.Companion.navigator

@Composable
fun DefaultPlaceHolder(title: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title)
            Button(onClick = {
                navigator.pop()
            }) {
                Text("Back")
            }
        }
    }
}