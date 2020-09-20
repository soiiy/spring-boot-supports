package com.soiiy.demo.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/feign")
class TestFeignController {

    @Autowired
    lateinit var service: TestFeignService

    @GetMapping
    fun index(user: DefaultUser): Any = user

    @GetMapping("/sms")
    fun sms(mobile: String): Any = service.sms(mobile)

    @GetMapping("/check")
    fun check(): Any = service.check()

}