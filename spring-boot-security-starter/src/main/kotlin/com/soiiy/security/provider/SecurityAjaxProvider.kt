package com.soiiy.security.provider

import com.hupoll.platform.util.json.JsonUtil
import com.soiiy.security.config.SecurityContextProperty
import com.soiiy.utils.http.ResponseUtil
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SecurityAjaxProvider(private val property: SecurityContextProperty):
    SecurityProvider {

    private val AJAX_HEADER = "XMLHttpRequest".toUpperCase()

    override fun order(): Int = 700

    override fun support(request: HttpServletRequest): Boolean {
        return request.headerNames.toList().contains("x-requested-with") &&
        request.getHeader("x-requested-with").toUpperCase() == AJAX_HEADER
    }

    override fun principal(request: HttpServletRequest): String? {
        val cookie = request.cookies.orEmpty().firstOrNull { it.name == property.keyName }
        return if (cookie === null) null else cookie.value
    }

    override fun failure(error: Throwable, response: HttpServletResponse) {
        val responseBody = JsonUtil.toJSONString(ResponseUtil.fail(error))
        response.contentType = "Application/json"
        response.characterEncoding = "utf-8"
        response.status = 401
        response.writer.print(responseBody)
    }

}