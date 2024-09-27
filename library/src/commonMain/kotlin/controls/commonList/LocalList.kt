package controls.commonList

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Plus
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kmp_library.library.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
inline fun <reified T : Any> LocalList(
    modifier: Modifier = Modifier,
    title: String = "",
    list: List<T>,
    noinline addClick: (() -> Unit) = defaultAddClick,
    crossinline itemContent: (@Composable (Int, T) -> Unit),
//    noinline itemClick: ((T) -> Unit) = {}
) {
    val vm: CommonListViewModel = remember { CommonListViewModel(list = list) }

    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("drawable/empty.json").decodeToString()
        )
    }
    val progress by animateLottieCompositionAsState(composition)

    Column(modifier = Modifier) {
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

            if (list.any()) {
                LazyColumn() {
                    itemsIndexed(list) { index, item ->
                        itemContent(index, item)
                    }
                }
            } else {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = rememberLottiePainter(
                            composition = composition,
                            progress = { progress }
                        ),
                        contentDescription = "Lottie animation"
                    )
                    Text("No hay items en la lista")
                }
            }
        }
    }
}