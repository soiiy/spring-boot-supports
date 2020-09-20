package com.soiiy.mall.module.wxma.http

import com.soiiy.mall.module.trade.data.TradeCreateDTO
import com.soiiy.mall.module.trade.data.TradeOrderResult
import com.soiiy.mall.module.trade.data.TradePrepayResult
import com.soiiy.mall.module.trade.service.TradeOrderService
import com.soiiy.utils.http.ResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wxma-orders")
class WxmaOrderController {

    @Autowired
    lateinit var service: TradeOrderService

    private val REFUND_TYPE: String = "PARTIAL_REFUNDING,PARTIAL_REFUNDED,PARTIAL_REFUND_FAILED,FULL_REFUNDING,FULL_REFUNDED,FULL_REFUND_FAILED"

    @GetMapping
    fun index(
        @RequestParam user: String,
        @RequestParam(required = false) type: String?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponsePage<TradeOrderResult> {
        // 全部 、 代付款 、 待收货 、 售后
        return when(type) {
            "REFUND" -> service.index(null, REFUND_TYPE, null, null, null, user, page, size)
            "WAIT_PAY" -> service.index("WAIT_PAY", null, null, null, null, user, page, size)
            "WAIT_CONFIRM" -> service.index("WAIT_CONFIRM", null, null, null, null, user, page, size)
            else -> service.index(null, null, null, null, null, user, page, size)
        }
    }

    @GetMapping("/{id}")
    fun show(@PathVariable id: String): TradeOrderResult = service.show(id)

    // 创建订单 - 预支付信息

    @PostMapping
    fun store(@RequestBody dto: TradeCreateDTO): TradePrepayResult = service.store(dto)

    @PutMapping("/{id}/prepay")
    fun prepay(@PathVariable id: String):  TradePrepayResult = service.prepay(service.findById(id))

    @PutMapping("/{id}/finish")
    fun finish(@PathVariable id: String):  TradeOrderResult = service.finish(id)

    @PutMapping("/{id}/cancel")
    fun cancel(@PathVariable id: String): TradeOrderResult = service.cancel(id)

    @PostMapping("/payment")
    fun payment(@RequestBody xmlBody: String): Any = service.payment(xmlBody)

}