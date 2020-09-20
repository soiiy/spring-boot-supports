package com.soiiy.demo.web

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "test",
    url = "http://mall.admin.soiiy.com"
)
interface TestFeignService {

    @GetMapping("/auth/sms")
    fun sms(@RequestParam mobile: String): String

    @GetMapping("/auth/check")
    fun check(): Any

}