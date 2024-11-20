package com.sweetmesoft.kmplibrary.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.TablerIcons
import compose.icons.tablericons.Search
import kmp_library.library.generated.resources.Res
import kmp_library.library.generated.resources.Search
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchControl(
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(Res.string.Search),
    value: String = "",
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(40.dp)
            )
            .background(Color.White)
    ) {
        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = Color.Black
            ),
            leadingIcon = {
                Icon(
                    imageVector = TablerIcons.Search,
                    contentDescription = stringResource(Res.string.Search),
                    tint = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp).size(16.dp)
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}