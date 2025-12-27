package com.sweetmesoft.kmpcontrols.controls

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
@OptIn(ExperimentalMaterial3Api::class)
fun SearchControl(
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(Res.string.Search),
    value: String = "",
    imeAction: ImeAction = ImeAction.Search,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.extraSmall,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        modifier = modifier.fillMaxWidth().border(
            1.dp, MaterialTheme.colorScheme.outline, shape
        ),
        value = value,
        enabled = enabled,
        maxLines = 1,
        onValueChange = { onValueChange(it) },
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction, capitalization = KeyboardCapitalization.Sentences
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onSearch(value)
            }),
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
                        modifier = Modifier.padding(start = 8.dp).size(16.dp)
                    )
                },
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                visualTransformation = VisualTransformation.None,
                contentPadding = PaddingValues(0.dp),
                shape = shape,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )
        })
}
