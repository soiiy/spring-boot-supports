package com.soiiy.mall.module.trade.data

import com.soiiy.utils.http.ShareRequest

class TradeCreateDTO: ShareRequest<TradeOrderEntity> {

    var tradeUser: String = ""

    var tradeItem: String = ""

    var quantity: Int = 1

    var extraInfo: Map<String, Any>? = null

}