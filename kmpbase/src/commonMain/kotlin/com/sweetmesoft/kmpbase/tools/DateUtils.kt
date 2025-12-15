package com.sweetmesoft.kmpbase.tools

import androidx.compose.runtime.Composable
import kmplibrary.kmpbase.generated.resources.Apr
import kmplibrary.kmpbase.generated.resources.Aug
import kmplibrary.kmpbase.generated.resources.Dec
import kmplibrary.kmpbase.generated.resources.Feb
import kmplibrary.kmpbase.generated.resources.Fri
import kmplibrary.kmpbase.generated.resources.Jan
import kmplibrary.kmpbase.generated.resources.Jul
import kmplibrary.kmpbase.generated.resources.Jun
import kmplibrary.kmpbase.generated.resources.Mar
import kmplibrary.kmpbase.generated.resources.May
import kmplibrary.kmpbase.generated.resources.Mon
import kmplibrary.kmpbase.generated.resources.Nov
import kmplibrary.kmpbase.generated.resources.Oct
import kmplibrary.kmpbase.generated.resources.Res
import kmplibrary.kmpbase.generated.resources.Sat
import kmplibrary.kmpbase.generated.resources.Sep
import kmplibrary.kmpbase.generated.resources.Sun
import kmplibrary.kmpbase.generated.resources.Thu
import kmplibrary.kmpbase.generated.resources.Tue
import kmplibrary.kmpbase.generated.resources.Wed
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DateTimeUnit.Companion.DAY
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import kotlin.time.Instant

fun getCurrentDateTime(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}

fun daysInMonth(year: Int, month: Int): Int {
    val nextMonth = if (month == 12) {
        LocalDate(year + 1, 1, 1)
    } else {
        LocalDate(year, month + 1, 1)
    }

    return nextMonth.minus(1, DAY).day
}

fun Instant.toLocalString(): String {
    return this.toLocalDateTime(TimeZone.currentSystemDefault()).toLocalString()
}

fun LocalDateTime.toLocalString(showSeconds: Boolean = false): String {
    val dateFormat = LocalDateTime.Format {
        date(LocalDate.Formats.ISO)
        char(' ')
        hour()
        char(':')
        minute()
        if (showSeconds) {
            char(':')
            second()
        }
    }

    return this.format(dateFormat)
}

fun LocalDate.toLocalString(): String {
    val dateFormat = LocalDate.Format {
        date(LocalDate.Formats.ISO)
    }

    return this.format(dateFormat)
}

@Composable
fun LocalDate.toLocalString(format: DateFormats = DateFormats.YYYY_MM_DD): String {
    val weekAbbreviated = listOf(
        stringResource(Res.string.Mon),
        stringResource(Res.string.Tue),
        stringResource(Res.string.Wed),
        stringResource(Res.string.Thu),
        stringResource(Res.string.Fri),
        stringResource(Res.string.Sat),
        stringResource(Res.string.Sun)
    )
    val monthsAbbreviated = listOf(
        stringResource(Res.string.Jan),
        stringResource(Res.string.Feb),
        stringResource(Res.string.Mar),
        stringResource(Res.string.Apr),
        stringResource(Res.string.May),
        stringResource(Res.string.Jun),
        stringResource(Res.string.Jul),
        stringResource(Res.string.Aug),
        stringResource(Res.string.Sep),
        stringResource(Res.string.Oct),
        stringResource(Res.string.Nov),
        stringResource(Res.string.Dec)
    )
    val dateFormat = when (format) {
        DateFormats.YYYY_MM_DD -> LocalDate.Format {
            date(LocalDate.Formats.ISO)
        }

        DateFormats.WWW_MMM_DD -> LocalDate.Format {
            dayOfWeek(DayOfWeekNames(weekAbbreviated))
            char(',')
            char(' ')
            monthName(MonthNames(monthsAbbreviated))
            char(' ')
            day(padding = Padding.ZERO)
        }
    }

    return this.format(dateFormat)
}

fun LocalDate.daysInMonth(): Int {
    val nextMonth = if (this.month.ordinal == 11) {
        LocalDate(year + 1, 1, 1)
    } else {
        LocalDate(year, this.month.ordinal + 2, 1)
    }
    return nextMonth.minus(1, DAY).day
}

fun LocalTime.toLocalString(showSeconds: Boolean = false): String {
    val timeFormat = LocalTime.Format {
        hour()
        char(':')
        minute()
        if (showSeconds) {
            char(':')
            second()
        }
    }

    return this.format(timeFormat)
}

fun LocalDateTime.plus(
    quantity: Int, unit: DateTimeUnit.DateBased
): LocalDateTime {
    var date = this.date
    date = date.plus(quantity, unit)
    return LocalDateTime(
        date.year, date.month, date.day, this.hour, this.minute, this.second, this.nanosecond
    )
}

fun LocalDateTime.minus(
    quantity: Int, unit: DateTimeUnit.DateBased
): LocalDateTime {
    var date = this.date
    date = date.minus(quantity, unit)
    return LocalDateTime(
        date.year, date.month, date.day, this.hour, this.minute, this.second, this.nanosecond
    )
}

enum class DateFormats(val number: Int) {
    YYYY_MM_DD(0),
    WWW_MMM_DD(1);

    companion object {
        fun fromNumber(number: Int): DateFormats? {
            return entries.find { it.number == number }
        }
    }
}