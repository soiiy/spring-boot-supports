package com.soiiy.utils.crypto

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object DigestUtil {

    private const val HEX_CHARS = "0123456789abcdef"

    private fun digest(algorithm: String, bytes: ByteArray): ByteArray {
        return try {
            MessageDigest.getInstance(algorithm).digest(bytes)
        } catch (ex: NoSuchAlgorithmException) {
            throw IllegalStateException("Could not find MessageDigest with algorithm \"$algorithm\"", ex)
        }
    }

    private fun encode(bytes: ByteArray): String {
        var result = ""
        bytes.forEach {
            val byte = it.toInt()
            result += HEX_CHARS[byte ushr 0x4 and 0xf]
            result += HEX_CHARS[byte and 0xf]
        }
        return result
    }

    fun md5(input: String): String = encode(digest("MD5", input.toByteArray()))

    fun sha1(input: String): String = encode(digest("SHA-1", input.toByteArray()))

    fun sha256(input: String): String = encode(digest("SHA-256", input.toByteArray()))

}