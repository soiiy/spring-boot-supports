package com.soiiy.mall.config

import com.soiiy.mall.module.trade.service.TradeOrderService
import com.soiiy.mall.support.MongoQueryProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime

@Configuration
@EnableScheduling
class TradeScheduleTask {

    @Autowired
    lateinit var service: TradeOrderService

    @Autowired
    lateinit var mongodb: MongoTemplate

    @Scheduled(fixedRate = 5000) // 5 秒执行一次
    private fun clear() {
        val orders = service.index("WAIT_PAY", null, null, null, null, null, 1, 20)
        for (order in orders.data.filter { it.createdAt.plusMinutes(15) < LocalDateTime.now() }) {
            service.cancel(order.id)
        }
    }

}