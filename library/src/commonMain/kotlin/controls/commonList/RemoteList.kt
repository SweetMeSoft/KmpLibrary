package controls.commonList

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Plus

val defaultAddClick: () -> Unit = {}

@Composable
inline fun <reified T : Any> RemoteList(
    modifier: Modifier = Modifier,
    url: String,
    title: String = "",
    crossinline itemContent: (@Composable (T) -> Unit),
    noinline addClick: (() -> Unit) = defaultAddClick
) {
    val vm: CommonListViewModel = remember { CommonListViewModel(url) }
//    val animationState by animateKottieCompositionAsState(
//        composition = rememberKottieComposition(
//            spec = KottieCompositionSpec.File(vm.state.animation)
//        ),
//        isPlaying = true
//    )

    Column(modifier = modifier.fillMaxSize()) {
        if (vm.state.isLoading) {
            vm.start<T>()
            androidx.compose.animation.AnimatedVisibility(
                visible = vm.state.isLoading, enter = fadeIn(), exit = fadeOut()
            ) {
                Column(
                    modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text("Cargando...")
                }
            }
        } else {
            if (title.isNotEmpty()) {
                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                    )

                    Spacer(Modifier.weight(1f))

                    if (addClick != defaultAddClick) {
                        Icon(imageVector = FontAwesomeIcons.Solid.Plus,
                            contentDescription = "Add list event",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(24.dp).clickable { addClick() })
                    }
                }

                Divider(
                    color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            if (vm.state.list.any()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(vm.state.list.size) { index ->
                        itemContent(vm.state.list[index] as T)
                    }
                }
            } else {
                Column(
                    modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    //TODO Search a better library to add lottie animations
//                    KottieAnimation(
//                        composition = animationState.composition,
//                        progress = { animationState.progress },
//                        modifier = modifier.size(300.dp)
//                    )
                    Text("No hay items en la lista")
                }
            }
        }
    }
}