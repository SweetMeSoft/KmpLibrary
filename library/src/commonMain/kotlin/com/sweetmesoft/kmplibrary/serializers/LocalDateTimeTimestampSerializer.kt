package com.sweetmesoft.kmplibrary.serializers

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.Instant

object LocalDateTimeTimestampSerializer : KSerializer<LocalDateTime> {
    override val descriptor = PrimitiveSerialDescriptor("TimestampString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val instant = value.toInstant(TimeZone.currentSystemDefault())
        val seconds = instant.epochSeconds
        val nanoseconds = instant.nanosecondsOfSecond
        val timestampString = "Timestamp(seconds=$seconds, nanoseconds=$nanoseconds)"
        encoder.encodeString(timestampString)
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val timestampString = decoder.decodeString()
        val regex = """Timestamp\(seconds=(\d+), nanoseconds=(\d+)\)""".toRegex()
        val matchResult = regex.find(timestampString)
            ?: throw IllegalArgumentException("La cadena no coincide con el formato 'Timestamp(seconds=..., nanoseconds=...)': $timestampString")
        val seconds = matchResult.groupValues[1].toLong()
        val nanoseconds = matchResult.groupValues[2].toInt()
        val instant = Instant.fromEpochSeconds(seconds, nanoseconds)
        return instant.toLocalDateTime(TimeZone.currentSystemDefault())
    }
}