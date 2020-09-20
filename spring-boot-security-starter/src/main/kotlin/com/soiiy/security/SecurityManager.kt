package com.soiiy.security

import com.soiiy.security.error.UnAuthenticationError
import com.soiiy.security.provider.SecurityProvider
import com.soiiy.security.support.SecurityContextProvider
import com.soiiy.security.storage.SecurityStorageEngine
import org.springframework.util.AntPathMatcher

/**
 * 登陆管理器
 */
class SecurityManager(val storage: SecurityStorageEngine) {

    private val matcher = AntPathMatcher()

    var target: Class<SecurityTarget> = SecurityTarget::class.java

    var permitUrls = listOf<String>()

    var providers = listOf<SecurityProvider>()

    private val isPermitUrl: Boolean get() {
        val uri = SecurityContextProvider.request().requestURI
        return permitUrls.any { matcher.match(it, uri) }
    }

    fun principal(): SecurityTarget? {
        val token = SecurityContextProvider.principal()
        val user = storage.findUserByToken(token, target)
        if (user === null && !isPermitUrl) throw UnAuthenticationError()
        return user
    }

    // 登陆
    fun save(user: SecurityTarget, channel: String = "default"): String = storage.saveBy(user, channel)

    fun refresh(token: String, user: SecurityTarget, channel: String = "default") = storage.refreshBy(token, user, channel)

    fun remove(token: String, channel: String = "default") = storage.removeBy(token, channel)

    fun clear(principal: String) = storage.clearBy(principal)

}