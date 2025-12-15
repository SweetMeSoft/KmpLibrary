package com.sweetmesoft.kmptestapp.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.sweetmesoft.kmplibrary.base.BaseScreen
import com.sweetmesoft.kmplibrary.base.BaseViewModel.Companion.navigator
import com.sweetmesoft.kmplibrary.objects.IconAction
import com.sweetmesoft.kmptestapp.screens.about.AboutScreen
import com.sweetmesoft.kmptestapp.screens.dialogs.DialogsScreen
import com.sweetmesoft.kmptestapp.screens.map.MapScreen
import com.sweetmesoft.kmptestapp.screens.pickers.PickersScreen
import compose.icons.TablerIcons
import compose.icons.tablericons.DotsVertical

class MainScreen : Screen {
    @Suppress("RequiresFeature")
    @Composable
    override fun Content() {
        remember { MainViewModel() }
        BaseScreen(
            toolbarColor = MaterialTheme.colorScheme.primary,
            toolbarIconsLight = false,
            title = "KMP TestApp",
            edgeToEdge = false,
            iconActions = listOf(
                IconAction(TablerIcons.DotsVertical, true) {
                    navigator.push(AboutScreen())
                })
        ) {
            rememberCoroutineScope()

            Column(
                modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text("Open Pickers") },
                    onClick = { navigator.push(PickersScreen()) })

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text("Open Dialogs") },
                    onClick = { navigator.push(DialogsScreen()) })

                //                rememberImageCropper()
                //                var openImagePicker by remember { mutableStateOf(value = false) }
                //                ProfilePhoto(
                //                    urlImage = vm.state.urlImage,
                //                ) {
                //                    openImagePicker = true
                //                }
                //
                //                CMPImagePickNCropDialog(
                //                    imageCropper = imageCropper,
                //                    openImagePicker = openImagePicker,
                //                    imagePickerDialogHandler = {
                //                        openImagePicker = it
                //                    },
                //                    selectedImageCallback = {
                //                        vm.updatePhoto(it)
                //                        selectedImage = it
                //                    },
                //                    selectedImageFileCallback = {
                //                    }
                //                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text("Open Map") },
                    onClick = { navigator.push(MapScreen()) })
            }
        }
    }
}
