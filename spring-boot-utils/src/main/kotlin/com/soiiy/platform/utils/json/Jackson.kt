package com.soiiy.platform.utils.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.math.BigDecimal

object Jackson {

    abstract class DecimalSerializer : JsonSerializer<Number>() {

        protected open val radix = BigDecimal(10)

        override fun serialize(num: Number?, generator: JsonGenerator, provider: SerializerProvider) {
            if (num === null) return
            generator.writeNumber(BigDecimal(num.toString()).divide(radix))
        }

    }

    abstract class DecimalDeserializer : JsonDeserializer<Number>() {

        protected open val radix = BigDecimal(10)

        override fun deserialize(json: JsonParser, context: DeserializationContext): Number {
            return try {
                BigDecimal(json.valueAsString).multiply(radix)
            } catch (e: Throwable) {
                e.printStackTrace()
                return 0
            }
        }

    }

    class Decimal1Serializer : DecimalSerializer()

    class Decimal1Deserializer : DecimalDeserializer()

    class Decimal2Serializer : DecimalSerializer() {
        override val radix = BigDecimal(100)
    }

    class Decimal2Deserializer : DecimalDeserializer() {
        override val radix = BigDecimal(100)
    }

    class Decimal3Serializer : DecimalSerializer() {
        override val radix = BigDecimal(1000)
    }

    class Decimal3Deserializer : DecimalDeserializer() {
        override val radix = BigDecimal(1000)
    }

    class Decimal4Serializer : DecimalSerializer() {
        override val radix = BigDecimal(10000)
    }

    class Decimal4Deserializer : DecimalDeserializer() {
        override val radix = BigDecimal(10000)
    }

}