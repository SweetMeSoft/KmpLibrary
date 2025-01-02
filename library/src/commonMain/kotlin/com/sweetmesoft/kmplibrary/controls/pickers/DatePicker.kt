package com.sweetmesoft.kmplibrary.controls.pickers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sweetmesoft.kmplibrary.controls.DialogFooter
import com.sweetmesoft.kmplibrary.tools.DateFormats
import com.sweetmesoft.kmplibrary.tools.daysInMonth
import com.sweetmesoft.kmplibrary.tools.disabledColorDark
import com.sweetmesoft.kmplibrary.tools.disabledColorText
import com.sweetmesoft.kmplibrary.tools.disabledColorTextDark
import com.sweetmesoft.kmplibrary.tools.toLocalString
import compose.icons.TablerIcons
import compose.icons.tablericons.ChevronLeft
import compose.icons.tablericons.ChevronRight
import kmp_library.library.generated.resources.Accept
import kmp_library.library.generated.resources.Cancel
import kmp_library.library.generated.resources.Date
import kmp_library.library.generated.resources.FridayOneLetter
import kmp_library.library.generated.resources.MondayOneLetter
import kmp_library.library.generated.resources.Res
import kmp_library.library.generated.resources.SaturdayOneLetter
import kmp_library.library.generated.resources.SundayOneLetter
import kmp_library.library.generated.resources.ThursdayOneLetter
import kmp_library.library.generated.resources.TuesdayOneLetter
import kmp_library.library.generated.resources.WednesdayOneLetter
import kotlinx.datetime.DateTimeUnit.Companion.MONTH
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import network.chaintech.kmp_date_time_picker.utils.MAX
import network.chaintech.kmp_date_time_picker.utils.MIN
import org.jetbrains.compose.resources.stringResource

@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    value: LocalDate,
    color: Color = MaterialTheme.colors.primary,
    enabled: Boolean = true,
    title: String = stringResource(Res.string.Date),
    minDate: LocalDate = LocalDate.MIN(),
    maxDate: LocalDate = LocalDate.MAX(),
    onSelectedDate: (LocalDate) -> Unit
) {
    var showPicker: Boolean by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        OutlinedTextField(
            value = value.toLocalString(),
            onValueChange = {},
            maxLines = 1,
            label = { Text(title) },
            readOnly = true,
            singleLine = true,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = color,
                cursorColor = color,
                focusedLabelColor = color,
            )
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp)
                .background(Color.Transparent)
                .clickable(enabled = enabled) {
                    showPicker = true
                }
        )
    }

    if (showPicker) {
        CalendarDatePicker(
            value = value,
            color = color,
            minDate = minDate,
            maxDate = maxDate,
            acceptText = stringResource(Res.string.Accept),
            cancelText = stringResource(Res.string.Cancel),
            onDateSelected = { selectedDate ->
                onSelectedDate(selectedDate)
                showPicker = false
            },
            onDismiss = { showPicker = false }
        )
    }
}

@Composable
private fun CalendarDatePicker(
    value: LocalDate,
    color: Color,
    acceptText: String,
    cancelText: String,
    minDate: LocalDate,
    maxDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedDate by remember { mutableStateOf(value) }
    var dateShown by remember { mutableStateOf(value) }
    var selectYear by remember { mutableStateOf(false) }
    val firstDayOfMonth = remember(dateShown.year, dateShown.monthNumber) {
        LocalDate(dateShown.year, dateShown.monthNumber, 1).dayOfWeek.ordinal % 7
    }
    val scrollState = rememberLazyGridState(dateShown.year - 1900)
    val daysOneLetter = listOf(
        stringResource(Res.string.MondayOneLetter),
        stringResource(Res.string.TuesdayOneLetter),
        stringResource(Res.string.WednesdayOneLetter),
        stringResource(Res.string.ThursdayOneLetter),
        stringResource(Res.string.FridayOneLetter),
        stringResource(Res.string.SaturdayOneLetter),
        stringResource(Res.string.SundayOneLetter)
    )

    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().background(
                    color = color,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ).padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.clickable {
                        if (!selectYear) {
                            selectYear = true
                        }
                    },
                    text = selectedDate.year.toString(),
                    color = Color.White
                )
                Text(
                    text = selectedDate.toLocalString(DateFormats.WWW_MMM_DD),
                    style = MaterialTheme.typography.h4,
                    color = Color.White
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
            ) {
                IconButton(onClick = {
                    dateShown = dateShown.minus(1, MONTH)
                }) {
                    Icon(TablerIcons.ChevronLeft, contentDescription = "Mes anterior")
                }
                Text(
                    text = dateShown.month.name + ", " + dateShown.year.toString(),
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                IconButton(onClick = {
                    dateShown = dateShown.plus(1, MONTH)
                }) {
                    Icon(TablerIcons.ChevronRight, contentDescription = "Mes siguiente")
                }
            }

            if (selectYear) {
                LazyVerticalGrid(
                    state = scrollState,
                    modifier = Modifier.padding(horizontal = 8.dp).height(290.dp),
                    columns = GridCells.Fixed(3),
                ) {
                    items(300) { year ->
                        val enabled = (year + 1900) >= minDate.year && (year + 1900) <= maxDate.year
                        Box(
                            modifier = Modifier
                                .background(
                                    if (year == dateShown.year) color else MaterialTheme.colors.surface,
                                )
                                .clickable(enabled = enabled) {
                                    dateShown = LocalDate(
                                        year + 1900,
                                        selectedDate.monthNumber,
                                        selectedDate.dayOfMonth
                                    )
                                    selectYear = false
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = 16.dp),
                                text = (year + 1900).toString(),
                                style = MaterialTheme.typography.body2,
                                color = if ((year + 1900) == dateShown.year) color else MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.padding(horizontal = 8.dp).height(290.dp),
                    columns = GridCells.Fixed(7),
                ) {
                    itemsIndexed(daysOneLetter) { index, day ->
                        Text(
                            text = day,
                            color = if (MaterialTheme.colors.isLight) disabledColorText else disabledColorTextDark,
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp
                        )
                    }

                    items(firstDayOfMonth) {
                        Box(modifier = Modifier.aspectRatio(1f)) {}
                    }
                    items(dateShown.daysInMonth()) { day ->
                        val thisDate = LocalDate(dateShown.year, dateShown.monthNumber, day + 1)
                        val enabled = thisDate in minDate..maxDate
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(CircleShape)
                                .background(if (thisDate == selectedDate) color else MaterialTheme.colors.surface)
                                .clickable(enabled = enabled) {
                                    selectedDate =
                                        LocalDate(
                                            dateShown.year,
                                            dateShown.monthNumber,
                                            day + 1
                                        )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (day + 1).toString(),
                                style = MaterialTheme.typography.body2,
                                color = if (!enabled) disabledColorDark else if (thisDate == selectedDate) Color.White else MaterialTheme.colors.onSurface
                            )
                        }
                    }
                }
            }

            DialogFooter(
                modifier = Modifier.align(alignment = Alignment.End),
                acceptText = acceptText,
                cancelText = cancelText,
                color = color,
                onAccept = {
                    onDateSelected(selectedDate)
                }
            ) {
                onDismiss()
            }
        }
    }
}