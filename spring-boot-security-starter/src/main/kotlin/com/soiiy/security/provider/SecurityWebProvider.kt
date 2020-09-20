package com.soiiy.security.provider

import com.soiiy.security.config.SecurityContextProperty
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SecurityWebProvider(private val property: SecurityContextProperty):
    SecurityProvider {

    override fun order(): Int = 900

    override fun support(request: HttpServletRequest): Boolean = true

    override fun principal(request: HttpServletRequest): String? {
        val cookie = request.cookies.orEmpty().firstOrNull { it.name == property.keyName }
        return if (cookie === null) null else cookie.value
    }

    override fun failure(error: Throwable, response: HttpServletResponse) {
        response.status = 401
        response.sendRedirect(property.redirect401)
    }

}