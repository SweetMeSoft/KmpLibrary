package controls.pickers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sweetmesoft.kmplibrary.controls.CalculatorPopup

@Composable
fun DoublePicker(
    modifier: Modifier = Modifier,
    title: String = "Monto",
    value: String = "",
    onValueChange: (Double) -> Unit
) {
    var showCalculator: Boolean by remember { mutableStateOf(false) }
    //var text: String by remember { mutableStateOf("") }
    Box(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = { },
            maxLines = 1,
            label = { Text(title) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            readOnly = true
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp)
                .background(Color.Transparent)
                .clickable {
                    showCalculator = true
                }
        )
    }

    CalculatorPopup(
        visible = showCalculator,
        onResult = {
            onValueChange(it)
            showCalculator = false
        })
}