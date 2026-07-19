package com.sweetmesoft.kmpcontrols.controls

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpControl(
    modifier: Modifier = Modifier,
    value: String,
    length: Int = 6,
    onValueChange: (String) -> Unit,
    isMasked: Boolean = false,
    colorLight: Color = MaterialTheme.colorScheme.primary,
    colorDark: Color = MaterialTheme.colorScheme.secondary,
    isError: Boolean = false
) {
    val focusRequester = remember { FocusRequester() }
    val activeColor = if (!isSystemInDarkTheme()) colorLight else colorDark
    val inactiveColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    val errorColor = MaterialTheme.colorScheme.error

    Box(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= length && newValue.all { it.isDigit() }) {
                    onValueChange(newValue)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier
                .size(1.dp)
                .focusRequester(focusRequester),
            textStyle = TextStyle(color = Color.Transparent)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { focusRequester.requestFocus() },
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until length) {
                val char = value.getOrNull(i)
                val isFocused = value.length == i
                
                val borderColor = when {
                    isError -> errorColor
                    isFocused -> activeColor
                    else -> inactiveColor
                }
                
                val borderWidth = if (isFocused) 2.dp else 1.dp
                
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .border(borderWidth, borderColor, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    val textToShow = when {
                        char != null -> if (isMasked) "•" else char.toString()
                        else -> ""
                    }
                    Text(
                        text = textToShow,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isError) errorColor else MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
