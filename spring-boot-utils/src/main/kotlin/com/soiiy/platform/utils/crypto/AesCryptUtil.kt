package com.soiiy.platform.utils.crypto

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object AesCryptUtil {

    private fun instance(mode: Int, secret: String): Cipher {
        val cipher = Cipher.getInstance("AES")
        val keySpec = SecretKeySpec(secret.toByteArray(),"AES")
        cipher.init(mode, keySpec)
        return cipher
    }

    fun encrypt(input: String, secret: String): String {
        val cipher = instance(Cipher.ENCRYPT_MODE, secret)
        val encrypt = cipher.doFinal(input.toByteArray());
        return Base64Util.encode(encrypt)
    }

    /**
     * aes解密
     */
    fun decrypt(input: String, secret: String): String {
        val cipher = instance(Cipher.DECRYPT_MODE, secret)
        val result = cipher.doFinal(Base64Util.decode(input))
        return String(result)
    }
}
