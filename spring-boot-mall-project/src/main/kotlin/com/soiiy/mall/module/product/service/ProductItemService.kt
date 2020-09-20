package com.soiiy.mall.module.product.service

import com.soiiy.mall.module.product.data.*
import com.soiiy.mall.module.product.mapper.ProductItemRepository
import com.soiiy.mall.module.product.mapper.ProductMenuRepository
import com.soiiy.mall.module.product.mapper.ProductTopicRepository
import com.soiiy.mall.support.MongoQueryProvider
import com.soiiy.mall.support.SpringBootUtil
import com.soiiy.utils.error.ArgumentInvalidError
import com.soiiy.utils.http.ResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service

@Service
class ProductItemService {

    @Autowired
    lateinit var service: ProductItemRepository

    @Autowired
    lateinit var menu: ProductMenuRepository

    @Autowired
    lateinit var topic: ProductTopicRepository

    @Autowired
    lateinit var mongodb: MongoQueryProvider

    fun query(): ProductItemQuery = ProductItemQuery()

    fun findById(id: String): ProductItemEntity = service.findById(id).orElseThrow { ArgumentInvalidError("数据不存在！") }

    fun show(id: String): ProductItemResult = result(findById(id))

    fun index(state: String?, menu: String?, topic: String?, keywords: String?, page: Int, size: Int): ResponsePage<ProductItemResult> {

        val where = Criteria()
        if (!state.isNullOrEmpty()) where.and("sellState").`is`(state)
        if (!menu.isNullOrEmpty()) where.and("itemMenu").`is`(menu)
        if (!topic.isNullOrEmpty()) where.and("itemTopic").`is`(topic)
        if (!keywords.isNullOrEmpty()) where.and("title").regex(keywords)

        val pageable = SpringBootUtil.dbMongoPageable("rankNum:DESC,updatedAt:DESC", page, size)

        val result  = mongodb.selectAll(where, pageable, ProductItemEntity::class.java)
        return ResponsePage(result.content.map { it.result() }, result.totalElements)
    }

    fun store(dto: ProductItemCreateDTO): ProductItemEntity {
        val entity = dto.entity()
        entity.stockTotal = entity.stockAvailable
        return service.save(dto.entity())
    }

    fun update(id: String, dto: ProductItemUpdateDTO): ProductItemEntity {
        val entity = dto.entity(findById(id))
        entity.stockTotal = entity.stockLocked + entity.stockSold + entity.stockAvailable
        return service.save(entity)
    }

    fun updateBySellState(id: String, state: String): ProductItemEntity {
        val entity = findById(id)
        entity.sellState = state
        return service.save(entity)
    }

    fun tradeNext(id: String, num: Int): ProductItemEntity {
        val item = findById(id)
        if (item.stockAvailable < num) throw ArgumentInvalidError("库存不足！")
        item.stockAvailable -= num
        item.stockSold += num
        return service.save(item)
    }

    fun tradeBack(id: String, num: Int): ProductItemEntity {
        val item = findById(id)
        item.stockAvailable += num
        item.stockSold -= num
        return service.save(item)
    }

    private fun result(entity: ProductItemEntity): ProductItemResult {
        val result = entity.result()
        if (result.itemMenu.isNotEmpty()) {
            menu.findById(result.itemMenu).ifPresent {
                result.itemMenuLabel = it.name
                result.itemMenuPicUrl = it.firstPicUrl
            }
        }
        if (!result.itemTopic.isNullOrEmpty()) {
            topic.findById(result.itemTopic.orEmpty()).ifPresent {
                result.itemTopicLabel = it.name
                result.itemTopicPicUrl = it.firstPicUrl
            }
        }
        return result
    }

}