package com.soiiy.platform.security.support

import com.soiiy.platform.security.SecurityTarget

object SecurityContextHolder {

    private val context = ThreadLocal<SecurityTarget?>()

    fun set(principal: SecurityTarget?) = context.set(principal)

    fun get(): SecurityTarget? = context.get()

    fun clear() = context.remove()

}