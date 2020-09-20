package com.soiiy.mall.module.wxma.http

import com.soiiy.mall.module.trade.service.TradeOrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wxma-users")
class WxmaUserController {

    @Autowired
    lateinit var service: TradeOrderService

    @GetMapping("/{id}/trade")
    fun trade(@PathVariable id: String): Any? = service.total(id)

}