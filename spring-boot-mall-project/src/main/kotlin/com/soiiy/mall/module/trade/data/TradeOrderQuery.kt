package com.soiiy.mall.module.trade.data

class TradeOrderQuery(
    var formData: Any? = null
) {

    var formTradeStates = mapOf(
        "WAIT_PAY" to "未付款",
        "WAIT_CONFIRM" to "待发货",
        "WAIT_FINISH" to "待收货",
        "FINISH" to "已完成"
    )

    var formRefundStates = mapOf(
        "NONE" to "无退款",
        "FULL_REFUNDING" to "退款中",
        "FULL_REFUNDED" to "退款完成"
    )

}