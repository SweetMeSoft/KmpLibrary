package com.sweetmesoft.kmpcontrols.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sweetmesoft.kmpbase.controls.alerts.BaseDialog
import com.sweetmesoft.kmpbase.theme.disabledColorDark
import com.sweetmesoft.kmpbase.theme.disabledColorText
import com.sweetmesoft.kmpbase.theme.disabledColorTextDark
import com.sweetmesoft.kmpbase.tools.DateFormats
import com.sweetmesoft.kmpbase.tools.daysInMonth
import com.sweetmesoft.kmpbase.tools.toLocalString
import dev.seyfarth.tablericons.TablerIcons
import dev.seyfarth.tablericons.outlined.ChevronLeft
import dev.seyfarth.tablericons.outlined.ChevronRight
import kmplibrary.kmpcontrols.generated.resources.Accept
import kmplibrary.kmpcontrols.generated.resources.Cancel
import kmplibrary.kmpcontrols.generated.resources.FridayOneLetter
import kmplibrary.kmpcontrols.generated.resources.MondayOneLetter
import kmplibrary.kmpcontrols.generated.resources.Res
import kmplibrary.kmpcontrols.generated.resources.SaturdayOneLetter
import kmplibrary.kmpcontrols.generated.resources.SundayOneLetter
import kmplibrary.kmpcontrols.generated.resources.ThursdayOneLetter
import kmplibrary.kmpcontrols.generated.resources.TuesdayOneLetter
import kmplibrary.kmpcontrols.generated.resources.WednesdayOneLetter
import kotlinx.datetime.DateTimeUnit.Companion.MONTH
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarDialog(
    isVisible: Boolean,
    value: LocalDate,
    color: Color = MaterialTheme.colorScheme.primary,
    acceptText: String = stringResource(Res.string.Accept),
    cancelText: String = stringResource(Res.string.Cancel),
    minDate: LocalDate = LocalDate(1900, 1, 1),
    maxDate: LocalDate = LocalDate(2100, 12, 31),
    onDismiss: () -> Unit = {},
    onDateSelected: (LocalDate) -> Unit
) {
    if (isVisible) {
        var selectedDate by remember { mutableStateOf(value) }
        var dateShown by remember { mutableStateOf(value) }
        var selectYear by remember { mutableStateOf(false) }
        val firstDayOfMonth = remember(dateShown.year, dateShown.month.number) {
            LocalDate(dateShown.year, dateShown.month.number, 1).dayOfWeek.ordinal % 7
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

        BaseDialog(
            acceptText = acceptText,
            cancelText = cancelText,
            color = color,
            onAccept = {
                onDateSelected(selectedDate)
            },
            onDismiss = { onDismiss() },
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().background(
                    color = color, shape = MaterialTheme.shapes.medium.copy(
                        bottomStart = CornerSize(0), bottomEnd = CornerSize(0)
                    )
                ).padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.clickable {
                        if (!selectYear) {
                            selectYear = true
                        }
                    },
                    text = selectedDate.year.toString(),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = selectedDate.toLocalString(DateFormats.WWW_MMM_DD),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                IconButton(onClick = {
                    dateShown = dateShown.minus(1, MONTH)
                }) {
                    Icon(
                        TablerIcons.Outlined.ChevronLeft,
                        contentDescription = "Mes anterior",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = dateShown.month.name + ", " + dateShown.year.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(onClick = {
                    dateShown = dateShown.plus(1, MONTH)
                }) {
                    Icon(
                        TablerIcons.Outlined.ChevronRight,
                        contentDescription = "Mes siguiente",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
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
                            modifier = Modifier.background(
                                if (year == dateShown.year) color else MaterialTheme.colorScheme.surface,
                            ).clickable(enabled = enabled) {
                                dateShown = LocalDate(
                                    year + 1900, selectedDate.month.number, selectedDate.day
                                )
                                selectYear = false
                            }, contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = 16.dp),
                                text = (year + 1900).toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if ((year + 1900) == dateShown.year) color else MaterialTheme.colorScheme.onSurface
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
                            color = if (!isSystemInDarkTheme()) disabledColorText else disabledColorTextDark,
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp
                        )
                    }

                    items(firstDayOfMonth) {
                        Box(modifier = Modifier.aspectRatio(1f)) {}
                    }
                    items(dateShown.daysInMonth()) { day ->
                        val thisDate = LocalDate(dateShown.year, dateShown.month.number, day + 1)
                        val enabled = thisDate in minDate..maxDate
                        Box(
                            modifier = Modifier.aspectRatio(1f).clip(CircleShape)
                                .background(if (thisDate == selectedDate) color else MaterialTheme.colorScheme.surface)
                                .clickable(enabled = enabled) {
                                    selectedDate = LocalDate(
                                        dateShown.year, dateShown.month.number, day + 1
                                    )
                                }, contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (day + 1).toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (!enabled) disabledColorDark else if (thisDate == selectedDate) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}