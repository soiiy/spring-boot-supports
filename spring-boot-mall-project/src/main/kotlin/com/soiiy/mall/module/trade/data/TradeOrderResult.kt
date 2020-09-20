package com.soiiy.mall.module.trade.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.soiiy.utils.json.Jackson
import java.time.LocalDateTime

class TradeOrderResult {

    var id: String = ""

    var tradeNo: String = ""

    var tradeItem: String = ""

    var title: String = ""

    var thumbnailUrl: String? = null

    var quantity: Int = 0


    @JsonSerialize(using = Jackson.Decimal2Serializer::class)
    @JsonDeserialize(using = Jackson.Decimal2Deserializer::class)
    var price: Int = 0

    // 交易状态
    /**
     * WAIT_PAY: 下单后 - 待支付
     * WAIT_CONFIRM: 支付后 - 待发货/发码
     * WAIT_FINISH: 发货/发码后 - 待核销/待收货
     * FINISH: 核销/收货后 - 已完成
     */
    var tradeState: String = "WAIT_PAY"

    // 退款状态
    /**
     *
    "NONE" to "无退款",
    "PARTIAL_REFUNDING" to "部分退款中",
    "PARTIAL_REFUNDED" to "部分退款完成",
    "PARTIAL_REFUND_FAILED" to "部分退款关闭",
    "FULL_REFUNDING" to "全部退款中",
    "FULL_REFUNDED" to "全部退款完成",
    "FULL_REFUND_FAILED" to "全部退款关闭"
     *
     */
    var refundState: String = "NONE"

    // 订单原始总金额
    @JsonSerialize(using = Jackson.Decimal2Serializer::class)
    @JsonDeserialize(using = Jackson.Decimal2Deserializer::class)
    var totalFee: Int = 0

    // 实际成交金额
    @JsonSerialize(using = Jackson.Decimal2Serializer::class)
    @JsonDeserialize(using = Jackson.Decimal2Deserializer::class)
    var totalAmount: Int = 0

    // 实际支付金额
    @JsonSerialize(using = Jackson.Decimal2Serializer::class)
    @JsonDeserialize(using = Jackson.Decimal2Deserializer::class)
    var totalPayment: Int = 0

    // 实际退款金额 - 累加
    @JsonSerialize(using = Jackson.Decimal2Serializer::class)
    @JsonDeserialize(using = Jackson.Decimal2Deserializer::class)
    var totalRefund: Int = 0

    var tradeUser: String = ""

    // 附加标签，可查询
    var extraTags: String? = null

    // 附加参数 - 收货人， 出行人
    var extraInfo: Map<String, Any>? = null

    var extraDeliver: Map<String, Any>? = null

    // 模糊查询关键字
    var keywords: String = ""


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var paymentAt: LocalDateTime? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var confirmAt: LocalDateTime? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var finishedAt: LocalDateTime? = null

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var refundedAt: LocalDateTime? = null

}