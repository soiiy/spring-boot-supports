package com.soiiy.utils.crypto

import java.util.*

object Base64Util {

    fun encode(input: String): String = encode(input.toByteArray())

    fun encode(input: ByteArray): String = Base64.getEncoder().encodeToString(input)

    fun decode(input: String): ByteArray = Base64.getDecoder().decode(input)

    fun decodeToString(input: String): String = String(decode(input))

}