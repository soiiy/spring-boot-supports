package com.soiiy.mall.module.trade.service

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest
import com.github.binarywang.wxpay.service.WxPayService
import com.soiiy.mall.module.product.service.ProductItemService
import com.soiiy.mall.module.trade.data.*
import com.soiiy.mall.module.trade.mapper.TradeOrderRepository
import com.soiiy.mall.support.MongoQueryProvider
import com.soiiy.mall.support.SpringBootUtil
import com.soiiy.utils.error.ArgumentInvalidError
import com.soiiy.utils.http.ResponsePage
import com.soiiy.utils.lang.StringUtil
import com.soiiy.utils.lang.TimeUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service
import java.net.InetAddress
import java.time.LocalDateTime

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Service
class TradeOrderService {

    @Autowired
    lateinit var pay: WxPayService

    @Autowired
    lateinit var prod: ProductItemService

    @Autowired
    lateinit var mongodb: MongoQueryProvider

    @Autowired
    lateinit var repository: TradeOrderRepository

    private val localHost = InetAddress.getLocalHost().hostAddress

    private val callbackUrl = "https://xppmall.soiiy.cn/wxma-orders/payment"

    fun findById(id: String): TradeOrderEntity {
        return repository.findById(id).orElseThrow { throw ArgumentInvalidError("不存在的订单：$id") }
    }

    fun findByTradeNo(tradeNo: String): TradeOrderEntity {
        return repository.findByTradeNo(tradeNo) ?: throw ArgumentInvalidError("不存在的订单：$tradeNo")
    }

    fun index(state: String?, refund: String?, start: String?, end: String?, keywords: String?, user: String?, page: Int, size: Int): ResponsePage<TradeOrderResult> {
        val where = Criteria()
        if (!state.isNullOrEmpty()) where.and("tradeState").`in`(state.split(","))
        if (!refund.isNullOrEmpty()) where.and("refundState").`in`(refund.split(","))
        if (!keywords.isNullOrEmpty()) where.and("keywords").regex(keywords)
        if (!user.isNullOrEmpty()) where.and("tradeUser").`is`(user)

        val pageable = SpringBootUtil.dbMongoPageable("createdAt:DESC", page, size)

        val result  = mongodb.selectAll(where, pageable, TradeOrderEntity::class.java)
        return ResponsePage(result.content.map { it.result() }, result.totalElements)
    }

    fun show(id: String): TradeOrderResult {
        val order = findById(id)
        if (order.tradeState == "WAIT_PAY") {
            if (order.createdAt.plusMinutes(15) < LocalDateTime.now()) cancel(id)
            else {
                val result = pay.queryOrder(null, order.tradeNo)
                if (result.resultCode == "SUCCESS" && result.returnCode == "SUCCESS" && result.tradeState == "SUCCESS") {
                    order.totalPayment = result.totalFee
                    order.paymentAt = LocalDateTime.now()
                    order.tradeState = "WAIT_CONFIRM"
                    order.updatedAt = LocalDateTime.now()
                    repository.save(order)
                }
            }
        }
        return order.result()
    }



    fun store(dto: TradeCreateDTO): TradePrepayResult {
        val order = dto.entity()
        order.tradeNo = TimeUtil.format("yyyyMMddHHmmss") + StringUtil.randomNumeric(6)
        val item = prod.tradeNext(order.tradeItem, order.quantity)
        val extras = order.extraInfo?.values.orEmpty().map { it.toString() }
        val keywords = listOf(order.tradeNo, item.title).plus(extras)
        order.keywords = keywords.joinToString(",")

        order.price = item.price
        order.title = item.title
        order.extraTags = item.extraTags
        order.thumbnailUrl = item.firstPicUrl

        val totalFee = item.price * order.quantity
        order.totalFee = totalFee
        order.totalAmount = totalFee
        repository.save(order)

        return prepay(order)
    }

    fun prepay(order: TradeOrderEntity): TradePrepayResult {
        val request = WxPayUnifiedOrderRequest()
        request.body = order.title
        request.spbillCreateIp = localHost
        request.body = order.title
        request.outTradeNo = order.tradeNo
        request.totalFee = order.totalAmount
        request.tradeType = "JSAPI"
        request.notifyUrl = callbackUrl
        request.subOpenid = order.tradeUser
        val result = pay.createOrder<WxPayMpOrderResult>(request)
        return TradePrepayResult(order.id, result)
    }


    fun confirm(id: String):  TradeOrderResult {
        val order = findById(id)
        if (order.tradeState == "WAIT_CONFIRM") {
            order.tradeState = "WAIT_FINISH"
            repository.save(order)
        }
        return order.result()
    }

    fun finish(id: String):  TradeOrderResult {
        val order = findById(id)
        if (order.tradeState == "WAIT_FINISH") {
            order.tradeState = "FINISH"
            order.finishedAt = LocalDateTime.now()
            repository.save(order)
        }
        return order.result()
    }

    fun payment(xmlData: String): String {
        println(xmlData)
        val result = pay.parseOrderNotifyResult(xmlData)

        if (result.resultCode != "SUCCESS" || result.returnCode != "SUCCESS") {
            return "FAILED!"
        }

        val order = findByTradeNo(result.outTradeNo)
        if (order.tradeState != "WAIT_PAY") return "SUCCESS!"

        order.totalPayment = result.totalFee
        order.paymentAt = LocalDateTime.now()
        order.tradeState = "WAIT_CONFIRM"
        order.updatedAt = LocalDateTime.now()
        repository.save(order)

        return "SUCCESS!"
    }


    fun total(user: String):  Any {
        val aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("tradeUser").`is`(user)),
            Aggregation.group("tradeState").count().`as`("tradeCount")
        )
        return mongodb.aggregate(aggregation, "trade_orders")
    }

    fun deliver(id: String, info: Map<String, Any>):  Any {
        val order = findById(id)
        order.extraDeliver = info
        order.tradeState = "WAIT_FINISH"
        order.confirmAt = LocalDateTime.now()
        order.updatedAt = LocalDateTime.now()
        return repository.save(order)
    }

    fun refund(id: String):  TradeOrderResult {
        val order = findById(id)
        if (order.tradeState == "CLOSE") return order.result()
        if (order.tradeState != "WAIT_PAY") {
            try {
                val query = pay.refundQuery(null, order.tradeNo, null, null)
                order.totalRefund = query.refundRecords.sumBy { it.refundFee }
            } catch (e: Exception) {
                val request = WxPayRefundRequest()
                request.outTradeNo = order.tradeNo
                request.outRefundNo = order.tradeNo
                request.refundFee = order.totalPayment
                request.totalFee = order.totalPayment
                val result = pay.refund(request)
                order.totalRefund = result.refundFee
            }
        }
        order.tradeState = "CLOSE"
        order.refundState = "FULL_REFUNDED"
        order.refundedAt = LocalDateTime.now()
        order.updatedAt = LocalDateTime.now()
        return repository.save(order).result()
    }

    fun cancel(id: String):  TradeOrderResult {
        val order = findById(id)
        if (order.tradeState == "WAIT_PAY") {
            prod.tradeBack(order.tradeItem, order.quantity)
            order.tradeState = "CLOSE"
            order.updatedAt = LocalDateTime.now()
        }
        return repository.save(order).result()
    }

}