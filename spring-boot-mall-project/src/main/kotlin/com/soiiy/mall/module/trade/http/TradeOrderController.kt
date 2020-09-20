package com.soiiy.mall.module.trade.http

import com.soiiy.mall.module.trade.data.*
import com.soiiy.mall.module.trade.service.TradeOrderService
import com.soiiy.utils.http.ResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/trade-orders")
class TradeOrderController {

    @Autowired
    lateinit var service: TradeOrderService

    @GetMapping("/query")
    fun query(): TradeOrderQuery = TradeOrderQuery()

    @GetMapping
    fun index(
        @RequestParam(required = false) state: String? = null,
        @RequestParam(required = false) refund: String? = null,
        @RequestParam(required = false) start: String? = null,
        @RequestParam(required = false) end: String? = null,
        @RequestParam(required = false) keywords: String? = null,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponsePage<TradeOrderResult> = service.index(state, refund, start, end, keywords, null, page, size)

    @GetMapping("/{id}")
    fun show(@PathVariable id: String): TradeOrderResult = service.show(id)

    @PutMapping("/{id}/deliver")
    fun deliver(@PathVariable id: String, @RequestBody info: Map<String, Any>): Any = service.deliver(id, info)

    @PutMapping("/{id}/refund")
    fun refund(@PathVariable id: String): Any = service.refund(id)

    @PutMapping("/{id}/cancel")
    fun cancel(@PathVariable id: String): Any = service.cancel(id)

}