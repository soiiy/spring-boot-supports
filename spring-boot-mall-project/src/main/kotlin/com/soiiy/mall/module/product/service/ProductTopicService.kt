package com.soiiy.mall.module.product.service

import com.soiiy.mall.module.product.data.ProductTopicDTO
import com.soiiy.mall.module.product.data.ProductTopicEntity
import com.soiiy.mall.module.product.mapper.ProductTopicRepository
import com.soiiy.mall.support.SpringBootUtil
import com.soiiy.utils.error.ArgumentInvalidError
import com.soiiy.utils.http.ResponsePage
import com.soiiy.utils.http.ResponseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductTopicService {

    @Autowired
    lateinit var service: ProductTopicRepository

    fun findById(id: String): ProductTopicEntity = service.findById(id).orElseThrow { ArgumentInvalidError("数据不存在！") }

    fun index(keywords: String?, page: Int, size: Int): ResponsePage<ProductTopicEntity> {
        val pageable = SpringBootUtil.dbMongoPageable("rankNum:DESC,updatedAt:DESC", page, size)
        val result = service.selectAllByKeywords(keywords.orEmpty(), pageable)
        return ResponsePage(result.content, result.totalElements)
    }

    fun store(dto: ProductTopicDTO) = service.save(dto.entity())

    fun update(id: String, dto: ProductTopicDTO) = service.save(dto.entity(findById(id)))

    fun destroy(id: String): String {
        service.deleteById(id)
        return ResponseUtil.SUCCESS
    }

}