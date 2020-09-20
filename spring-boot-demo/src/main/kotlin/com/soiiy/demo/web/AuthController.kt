package com.soiiy.demo.web

import com.soiiy.security.SecurityManager
import com.soiiy.utils.lang.StringUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    lateinit var manager: SecurityManager

    @PostMapping("/login")
    fun login(): AuthLoginResult {
        val user = DefaultUser(
            StringUtil.random(6),
            StringUtil.random(6)
        )

        /**
         * 登陆 - 换取token
         */
        val token = manager.save(user)
        return AuthLoginResult(token, user)
    }

    @GetMapping("/login")
    fun page(): String = "这是登陆页面！"

}