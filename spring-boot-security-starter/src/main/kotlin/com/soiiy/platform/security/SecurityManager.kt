package com.soiiy.platform.security

import com.soiiy.platform.security.error.UnAuthenticationError
import com.soiiy.platform.security.provider.SecurityProvider
import com.soiiy.platform.security.support.SecurityContextProvider
import com.soiiy.platform.security.storage.SecurityStorageEngine
import org.springframework.util.AntPathMatcher

/**
 * 登陆管理器
 */
class SecurityManager(private val storage: SecurityStorageEngine) {

    private val matcher = AntPathMatcher()

    var target: Class<_root_ide_package_.com.soiiy.platform.security.SecurityTarget> = _root_ide_package_.com.soiiy.platform.security.SecurityTarget::class.java

    var permitUrls = listOf<String>()

    var providers = listOf<SecurityProvider>()

    private val isPermitUrl: Boolean get() {
        val uri = SecurityContextProvider.request().requestURI
        return permitUrls.any { matcher.match(it, uri) }
    }

    fun principal(): _root_ide_package_.com.soiiy.platform.security.SecurityTarget? {
        val token = SecurityContextProvider.principal()
        val user = storage.findUserByToken(token, target)
        if (user === null && !isPermitUrl) throw UnAuthenticationError()
        return user
    }

    // 登陆
    fun save(user: _root_ide_package_.com.soiiy.platform.security.SecurityTarget, channel: String = "default"): String = storage.saveBy(user, channel)

    fun refresh(token: String, user: _root_ide_package_.com.soiiy.platform.security.SecurityTarget, channel: String = "default") = storage.refreshBy(token, user, channel)

    fun remove(token: String, channel: String = "default") = storage.removeBy(token, channel)

    fun clear(principal: String) = storage.clearBy(principal)

}