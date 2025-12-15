package com.sweetmesoft.kmpbase.serializers

import com.sweetmesoft.kmpbase.tools.toLocalString
import kotlinx.datetime.LocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toLocalString())
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        var stringValue = decoder.decodeString()
        stringValue = stringValue.substringBefore('T')
        return LocalDate.parse(stringValue)
    }
}