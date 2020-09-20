package com.soiiy.security.support

import com.soiiy.security.error.UnAuthenticationError
import com.soiiy.utils.crypto.AesCryptUtil
import com.soiiy.utils.crypto.DigestUtil
import com.soiiy.utils.lang.StringUtil
import com.soiiy.utils.lang.TimeUtil

object SecurityTokenUtil {

    private const val AES_SECRET = "SOIIY@SECURITY!#"

    private const val TOKEN_PREFIX = "SecurityToken@"

    fun make(principal: String, expireDay: Long): String {
        val time = TimeUtil.timeSecond()
        val token = mapOf(
            "principal" to principal,
            "random" to StringUtil.random(32),
            "expire" to TimeUtil.timeSecond() + expireDay * 86400
        ).toList().sortedBy { it.first }.joinToString("&") { it.first + "=" + it.second }

        val sign = DigestUtil.sha1(token)
        return TOKEN_PREFIX + AesCryptUtil.encrypt("$token&sign=$sign", AES_SECRET)
    }

    fun take(token: String): String {

        val decrypt = try {
            AesCryptUtil.decrypt(token.substringAfter("@"), AES_SECRET)
        } catch (e: Exception) {
            throw UnAuthenticationError("无效凭证，需要登陆！")
        }

        val sign = decrypt.substringAfterLast("=")
        val dist = decrypt.substringBeforeLast("&")

        if (sign != DigestUtil.sha1(dist)) throw UnAuthenticationError("无效凭证，需要登陆！")

        val result = dist.split("&").map {
            val args = it.split("=")
            return@map Pair(args.first(), args.last())
        }.toMap()

        val principal =  result["principal"]

        if (principal.isNullOrEmpty() || result.size != 3) {
            throw UnAuthenticationError("无效凭证，需要登陆！")
        }

        // 不存在有效期或者已过期
        if (TimeUtil.timeSecond() > (result["expire"]?.toLong() ?: 0)) {
            throw UnAuthenticationError("凭证已过期，请重新登陆！")
        }

        return principal

    }

}