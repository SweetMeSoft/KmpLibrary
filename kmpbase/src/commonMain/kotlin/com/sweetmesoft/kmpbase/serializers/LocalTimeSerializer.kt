package com.sweetmesoft.kmpbase.serializers

import com.sweetmesoft.kmpbase.tools.toLocalString
import kotlinx.datetime.LocalTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object LocalTimeSerializer : KSerializer<LocalTime> {
    override val descriptor = PrimitiveSerialDescriptor("LocalTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalTime) {
        encoder.encodeString(value.toLocalString(true))
    }

    override fun deserialize(decoder: Decoder): LocalTime {
        val stringValue = decoder.decodeString()
        return LocalTime.parse(stringValue)
    }
}