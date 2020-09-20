package com.soiiy.mall.module.trade.mapper

import com.soiiy.mall.module.trade.data.TradeOrderEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TradeOrderRepository: MongoRepository<TradeOrderEntity, String> {

    fun findByTradeNo(tradeNo: String): TradeOrderEntity?

}