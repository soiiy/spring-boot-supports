package com.soiiy.mall.module.admin.http

import com.soiiy.mall.module.admin.data.AdminAuthLoginDTO
import com.soiiy.mall.module.admin.data.AdminAuthLoginResult
import com.soiiy.mall.module.admin.service.AdminAuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/admin-auth")
class AdminAuthController {

    @Autowired
    lateinit var service: AdminAuthService

    @PostMapping("/login")
    fun login(@RequestBody dto: AdminAuthLoginDTO, response: HttpServletResponse): AdminAuthLoginResult {
        val result = service.login(dto)
        val cookie = Cookie("SecurityAuthorization", result.token)
        cookie.maxAge = 30 * 86400
        cookie.path = "/"
        response.addCookie(cookie)
        return result
    }

    @GetMapping("/login")
    fun logout(
            @CookieValue(name = "SecurityAuthorization", required = false) token: String?,
            response: HttpServletResponse
    ): String {
        val cookie = Cookie("SecurityAuthorization", "")
        cookie.maxAge = 0
        response.addCookie(cookie)
        if (token.isNullOrEmpty()) return "SUCCESS!"
        return service.logout(token)
    }

}