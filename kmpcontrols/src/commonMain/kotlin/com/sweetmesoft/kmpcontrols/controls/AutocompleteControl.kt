package com.sweetmesoft.kmpcontrols.controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties

private class BelowTextFieldPositionProvider : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        // Return absolute window coordinates for correct popup positioning
        return IntOffset(x = anchorBounds.left, y = anchorBounds.bottom)
    }
}

@Composable
fun AutoCompleteTextField(
    modifier: Modifier = Modifier,
    value: String,
    title: String,
    suggestions: List<String>,
    onValueChange: (String) -> Unit,
    onSuggestionSelected: (String) -> Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var textFieldWidth by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                    expanded = it.isNotBlank() && suggestions.isNotEmpty()
                },
                label = { Text(title) },
                keyboardOptions = KeyboardOptions(KeyboardCapitalization.Sentences),
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { size ->
                        textFieldWidth = size.width
                    },
                singleLine = true
            )

            if (expanded && suggestions.isNotEmpty()) {
                val textFieldWidthDp = with(density) { textFieldWidth.toDp() }
                
                Popup(
                    popupPositionProvider = BelowTextFieldPositionProvider(),
                    properties = PopupProperties(focusable = false),
                    onDismissRequest = { expanded = false }
                ) {
                    Surface(
                        modifier = Modifier.width(textFieldWidthDp),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surface,
                        shadowElevation = 6.dp,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            suggestions.forEach { suggestion ->
                                DropdownMenuItem(
                                    onClick = {
                                        expanded = false
                                        focusManager.clearFocus()
                                        onSuggestionSelected(suggestion)
                                    },
                                    text = { Text(text = suggestion) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}