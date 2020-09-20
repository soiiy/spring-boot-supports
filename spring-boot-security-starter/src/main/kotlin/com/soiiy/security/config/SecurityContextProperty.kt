package com.soiiy.security.config

import com.soiiy.security.SecurityTarget
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.security")
class SecurityContextProperty {

    var sso: Boolean = false

    var mode: String = "Memory"

    // 自定义命名
    var keyName: String = "SecurityAuthorization"

    lateinit var target: Class<SecurityTarget>

    var expireDay: Long = 90

    @Value("\${redirect-401:/auth/401}")
    var redirect401: String = "/auth/401"

    @Value("\${redirect-403:/auth/403}")
    var redirect403: String = "/auth/403"

    @Value("\${permit-urls:/,/auth/**}.split(',')")
    lateinit var permitUrls: List<String>

}
