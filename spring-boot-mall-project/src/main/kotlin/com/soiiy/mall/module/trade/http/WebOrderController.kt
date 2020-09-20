package com.soiiy.mall.module.trade.http

import com.soiiy.mall.module.trade.service.TradeOrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/order")
class WebOrderController {

    @Autowired
    lateinit var service: TradeOrderService

    @GetMapping
    fun index(): ModelAndView = ModelAndView("order/index")

    @GetMapping("/{id}")
    fun show(@PathVariable("id") id: String): ModelAndView {
        return ModelAndView("order/show", mapOf("trade" to service.show(id)))
    }

}