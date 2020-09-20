package com.soiiy.platform.security.provider

import com.hupoll.platform.util.json.JsonUtil
import com.soiiy.platform.security.config.SecurityContextProperty
import com.soiiy.platform.utils.http.ResponseUtil
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SecurityJsonProvider(private val property: SecurityContextProperty):
    SecurityProvider {

    override fun order(): Int = 800

    override fun support(request: HttpServletRequest): Boolean {
        return !request.contentType.isNullOrEmpty() && request.contentType.contains("json")
    }

    override fun principal(request: HttpServletRequest): String? {
        return request.getHeader(property.keyName).orEmpty()
    }

    override fun failure(error: Throwable, response: HttpServletResponse) {
        val responseBody = JsonUtil.toJSONString(ResponseUtil.fail(error))
        response.contentType = "Application/json"
        response.characterEncoding = "utf-8"
        response.status = 401
        response.writer.print(responseBody)
    }

}