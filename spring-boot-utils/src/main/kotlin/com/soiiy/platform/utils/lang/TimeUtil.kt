package com.soiiy.platform.utils.lang

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeUtil {

    fun timeMillis(): Long = System.currentTimeMillis()

    fun timeSecond(): Long = timeMillis() / 1000

    fun timeToRadix(radix: Int): String = System.currentTimeMillis().toString(radix)

    fun format(pattern: String): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern))
    }

}