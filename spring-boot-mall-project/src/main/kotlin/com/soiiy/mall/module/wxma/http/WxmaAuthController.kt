package com.soiiy.mall.module.wxma.http

import cn.binarywang.wx.miniapp.api.WxMaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wxma-auth")
class WxmaAuthController {

    @Autowired
    lateinit var service: WxMaService

    @PostMapping("/login")
    fun login(@RequestBody jscode: String): String {
        val result = service.userService.getSessionInfo(jscode)
        return result.openid
    }

}