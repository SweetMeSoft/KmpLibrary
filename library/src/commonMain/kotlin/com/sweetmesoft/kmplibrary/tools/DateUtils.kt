package com.sweetmesoft.kmplibrary.tools

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

fun getCurrentDateTime(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}

fun Instant.toLocalString(): String {
    return this.toLocalDateTime(TimeZone.currentSystemDefault()).toLocalString()
}

fun LocalDateTime.toLocalString(): String {
    val dateFormat = LocalDateTime.Format {
        date(LocalDate.Formats.ISO)
        char(' ')
        hour()
        char(':')
        minute()
        char(':')
        second()
    }

    return this.format(dateFormat)
}

fun LocalDate.toLocalString(): String {
    val dateFormat = LocalDate.Format {
        date(LocalDate.Formats.ISO)
    }

    return this.format(dateFormat)
}

fun LocalTime.toLocalString(showSeconds: Boolean = false): String {
    val timeFormat = if (showSeconds) {
        LocalTime.Format {
            hour()
            char(':')
            minute()
            char(':')
            second()
        }
    } else {
        LocalTime.Format {
            hour()
            char(':')
            minute()
        }
    }

    return this.format(timeFormat)
}