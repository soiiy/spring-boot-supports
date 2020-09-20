package com.soiiy.security.provider

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface SecurityProvider {

    fun order(): Int

    fun support(request: HttpServletRequest): Boolean

    fun principal(request: HttpServletRequest): String?

    fun failure(error: Throwable, response: HttpServletResponse) = Unit
}