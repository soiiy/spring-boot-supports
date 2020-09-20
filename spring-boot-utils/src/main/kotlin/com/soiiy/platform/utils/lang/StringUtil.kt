package com.soiiy.platform.utils.lang

import java.util.*

object StringUtil {

    private const val RANDOM_NUMERIC_CHARS = "1234567890"

    private const val RANDOM_DEFAULT_CHARS = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM"

    fun random(len: Int): String = random(
        len,
        RANDOM_DEFAULT_CHARS
    )

    fun randomNumeric(len: Int): String = random(
        len,
        RANDOM_NUMERIC_CHARS
    )

    fun random(len: Int, chars: String): String {
        return (len downTo 1).map { chars.random() }.joinToString("")
    }

}