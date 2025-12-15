package com.sweetmesoft.kmpcontrols.dialogs

import androidx.annotation.RequiresFeature
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sweetmesoft.kmpbase.controls.alerts.BaseDialog
import com.sweetmesoft.kmpbase.theme.disabledColor
import com.sweetmesoft.kmpbase.theme.disabledColorDark
import com.sweetmesoft.kmpbase.theme.disabledColorText
import com.sweetmesoft.kmpbase.theme.disabledColorTextDark
import com.sweetmesoft.kmpbase.tools.toDegrees
import com.sweetmesoft.kmpbase.tools.toRadians
import com.sweetmesoft.kmpcontrols.tools.Vibrator
import kmplibrary.kmpcontrols.generated.resources.Accept
import kmplibrary.kmpcontrols.generated.resources.Cancel
import kmplibrary.kmpcontrols.generated.resources.Res
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource
import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@RequiresFeature(name = "VIBRATE Android Permission", enforcement = "")
@Composable
fun ClockDialog(
    isVisible: Boolean,
    value: LocalTime,
    color: Color = MaterialTheme.colorScheme.primary,
    acceptText: String = stringResource(Res.string.Accept),
    cancelText: String = stringResource(Res.string.Cancel),
    onDismiss: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit
) {
    if (isVisible) {
        var selectedHour by remember { mutableStateOf(value.hour) }
        var selectedMinute by remember { mutableStateOf(value.minute) }
        var isHourSelection by remember { mutableStateOf(true) }

        BaseDialog(acceptText = acceptText, cancelText = cancelText, color = color, onAccept = {
            onTimeSelected(LocalTime(selectedHour, selectedMinute))
        }, onDismiss = {
            onDismiss()
        }) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TimeDialogHeader(
                    modifier = Modifier.fillMaxWidth().background(
                        color = color, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    ).padding(16.dp),
                    hour = selectedHour,
                    minute = selectedMinute,
                    isHourSelection = isHourSelection,
                    onHourClick = { isHourSelection = true },
                    onMinuteClick = { isHourSelection = false })

                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimeSelector(
                        isHour = isHourSelection,
                        color = color,
                        selectedValue = if (isHourSelection) selectedHour else selectedMinute
                    ) { value ->
                        if (isHourSelection) {
                            selectedHour = value
                        } else {
                            selectedMinute = value
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TimeDialogHeader(
    modifier: Modifier = Modifier,
    hour: Int,
    minute: Int,
    isHourSelection: Boolean,
    onHourClick: () -> Unit,
    onMinuteClick: () -> Unit
) {
    val disabledColor = if (!isSystemInDarkTheme()) disabledColorText else disabledColorTextDark
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = hour.toString().padStart(2, '0'), style = TextStyle(
                fontSize = 60.sp, color = if (!isHourSelection) disabledColor else Color.White
            ), modifier = Modifier.padding(4.dp).clickable { onHourClick() })
        Text(
            text = ":", style = TextStyle(fontSize = 60.sp, color = Color.White)
        )
        Text(
            text = minute.toString().padStart(2, '0'), style = TextStyle(
                fontSize = 60.sp, color = if (isHourSelection) disabledColor else Color.White
            ), modifier = Modifier.padding(4.dp).clickable { onMinuteClick() })
    }
}

@Composable
private fun TimeSelector(
    modifier: Modifier = Modifier,
    isHour: Boolean,
    color: Color,
    selectedValue: Int,
    onValueChange: (Int) -> Unit
) {
    with(LocalDensity.current) {
        val valuesExternal = if (isHour) (0..11).toList() else (0..59).toList()
        val valuesInternal = if (isHour) (12..23).toList() else emptyList()
        val radiusExternal = remember { 90.dp }
        val radiusInternal = remember { 55.dp }
        val radiusPxExternal = remember { radiusExternal.toPx() }
        val radiusPxInternal = remember { radiusInternal.toPx() }
        val radiusPxExternalExtended = remember { radiusPxExternal * 1.25f }
        val radiusPxInternalExtended = remember { radiusPxInternal * 1.25f }
        var previousValue = remember { 0 }

        Box(
            modifier = modifier.size(radiusExternal * 2.5f).clip(CircleShape)
                .background(if (!isSystemInDarkTheme()) disabledColor else disabledColorDark)
                .pointerInput(isHour) {
                    detectDragGestures { change, dragAmount ->
                        val x = change.position.x - radiusPxExternalExtended
                        val y = change.position.y - radiusPxExternalExtended
                        val angle = getAngleFromCoordinates(x, y)
                        var value = getClosestValue(angle, valuesExternal.size)
                        if (isHour && x.absoluteValue < radiusPxInternalExtended && y.absoluteValue < radiusPxInternalExtended) {
                            value += 12
                        }
                        onValueChange(value)
                        if (previousValue != value) {
                            previousValue = value
                            Vibrator.vibrate()
                        }
                    }
                }, contentAlignment = Alignment.Center
        ) {
            ClockLine(
                color = color,
                radius = if (valuesExternal.contains(selectedValue)) radiusExternal else radiusInternal,
                selectedValue = selectedValue,
                values = valuesExternal
            )

            valuesExternal.forEach { value ->
                val angleDegrees = 360.0 / valuesExternal.size * value
                val angleRadians = angleDegrees.toRadians()
                val xOffset = (radiusPxExternal * sin(angleRadians)).toFloat()
                val yOffset = (-radiusPxExternal * cos(angleRadians)).toFloat()
                Box(
                    modifier = Modifier.offset(x = xOffset.toDp(), y = yOffset.toDp()).size(40.dp)
                        .clip(CircleShape).clickable { onValueChange(value) }.background(
                            if (value == selectedValue) color else Color.Transparent
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (isHour) value.toString() else if (value % 5 == 0) value.toString()
                            .padStart(2, '0') else "", style = TextStyle(
                            fontSize = 16.sp,
                            color = if (value == selectedValue) Color.White else MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }

            valuesInternal.forEach { value ->
                val angle = (360.0 / valuesInternal.size * value).toRadians()
                val xOffset = (radiusPxInternal * sin(angle)).toFloat()
                val yOffset = (-radiusPxInternal * cos(angle)).toFloat()
                Box(
                    modifier = Modifier.offset(x = xOffset.toDp(), y = yOffset.toDp()).size(40.dp)
                        .clip(CircleShape).clickable { onValueChange(value) }.background(
                            if (value == selectedValue) color else Color.Transparent
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = value.toString(), style = TextStyle(
                            fontSize = 14.sp,
                            color = if (value == selectedValue) Color.White else Color.Gray
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ClockLine(color: Color, radius: Dp, selectedValue: Int, values: List<Int>) {
    with(LocalDensity.current) {
        val radiusPx = radius.toPx()
        val centerPx = (radius * 1.25f).toPx()
        val selectedAngle = (360.0 / values.size * selectedValue).toRadians()
        val selectedXOffset = (radiusPx * sin(selectedAngle)).toFloat()
        val selectedYOffset = (-radiusPx * cos(selectedAngle)).toFloat()

        Canvas(
            modifier = Modifier.size(radius * 2.5f)
        ) {
            drawLine(
                color = color,
                start = Offset(centerPx, centerPx),
                end = Offset(centerPx + selectedXOffset, centerPx + selectedYOffset),
                strokeWidth = 8f
            )

            drawCircle(
                color = color, radius = 16f, center = Offset(centerPx, centerPx)
            )
        }
    }
}

private fun getAngleFromCoordinates(x: Float, y: Float): Float {
    val angleRadians = atan2(y, x)
    return ((angleRadians.toDegrees() + 360) % 360) + 90
}

private fun getClosestValue(angle: Float, totalValues: Int): Int {
    val anglePerValue = 360f / totalValues
    return ((angle + anglePerValue / 2) / anglePerValue).toInt() % totalValues
}