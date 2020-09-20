package com.soiiy.platform.security.storage

import com.soiiy.platform.security.config.SecurityContextProperty

class SecurityStorageInMemory(private val property: SecurityContextProperty):
    SecurityStorageEngine {

    private val map = mutableMapOf<String, String>()

    override fun isSingle(): Boolean = property.sso

    override fun expireDay(): Long = property.expireDay

    override fun find(token: String): String? = map[token].orEmpty()

    override fun save(token: String, info: String) = map.set(token, info)

    override fun remove(token: String) {
        map.remove(token)
    }

}