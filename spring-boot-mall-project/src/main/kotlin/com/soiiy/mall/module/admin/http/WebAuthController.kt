package com.soiiy.mall.module.admin.http

import com.hupoll.platform.util.json.JsonUtil
import com.soiiy.security.support.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/auth")
class WebAuthController {

    @GetMapping("/login")
    fun login(): ModelAndView {
        println(JsonUtil.toJSONString(SecurityContextHolder.get()))
        return ModelAndView("auth/login")
    }

}