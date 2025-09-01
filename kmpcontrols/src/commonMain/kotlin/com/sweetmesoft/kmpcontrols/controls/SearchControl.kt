package com.sweetmesoft.kmpcontrols.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.TablerIcons
import compose.icons.tablericons.Search
import kmplibrary.kmpcontrols.generated.resources.Res
import kmplibrary.kmpcontrols.generated.resources.Search
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchControl(
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(Res.string.Search),
    value: String = "",
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Default,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(40.dp)
            )
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CustomBasicTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            onSearch = onSearch,
            imeAction = imeAction,
            enabled = enabled
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomBasicTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    imeAction: ImeAction,
    enabled: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = value,
        enabled = enabled,
        maxLines = 1,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = if (!isSystemInDarkTheme()) Color.Black else Color.White,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            capitalization = KeyboardCapitalization.Sentences
        ),
        cursorBrush = SolidColor(if (!isSystemInDarkTheme()) Color.Black else Color.White),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onSearch(value)
            }
        ),
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                placeholder = {
                    Text(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = TablerIcons.Search,
                        contentDescription = "Buscar",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(16.dp)
                    )
                },
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                visualTransformation = VisualTransformation.None,
                contentPadding = PaddingValues(0.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )
        }
    )
}