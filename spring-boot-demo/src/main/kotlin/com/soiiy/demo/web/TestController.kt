package com.soiiy.demo.web

import com.soiiy.security.annotation.SecurityUser
import com.soiiy.utils.error.RuntimeError
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestController {

    class RunError: RuntimeError(101, "服务器响应失败！")

    @GetMapping
    fun index(): Any? = "测试页面"

    @DeleteMapping
    fun demo(@RequestBody body: String): String = "aaaaa"

    @GetMapping("/a")
    fun grantA(user: DefaultUser?): Any? = user

    @GetMapping("/b")
    fun grantB(@SecurityUser user: DefaultUser): Any = user

}