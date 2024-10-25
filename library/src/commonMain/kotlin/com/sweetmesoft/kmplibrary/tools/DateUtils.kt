package com.sweetmesoft.kmplibrary.tools

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

fun getCurrentDateTime(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}

fun Instant.toLocalString(): String {
    val localDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val dateFormat = LocalDateTime.Format {
        date(LocalDate.Formats.ISO)
        char(' ')
        hour()
        char(':')
        minute()
        char(':')
        second()
    }

    return localDateTime.format(dateFormat)
}

