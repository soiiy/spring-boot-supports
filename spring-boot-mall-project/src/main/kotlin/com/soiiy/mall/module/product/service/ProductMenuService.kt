package com.soiiy.mall.module.product.service

import com.soiiy.mall.module.product.data.ProductMenuDTO
import com.soiiy.mall.module.product.data.ProductMenuEntity
import com.soiiy.mall.module.product.mapper.ProductMenuRepository
import com.soiiy.mall.support.SpringBootUtil
import com.soiiy.utils.error.ArgumentInvalidError
import com.soiiy.utils.http.ResponsePage
import com.soiiy.utils.http.ResponseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductMenuService {

    @Autowired
    lateinit var service: ProductMenuRepository

    fun findById(id: String): ProductMenuEntity = service.findById(id).orElseThrow { ArgumentInvalidError("数据不存在！") }

    fun index(page: Int, size: Int): ResponsePage<ProductMenuEntity> {
        val pageable = SpringBootUtil.dbMongoPageable("rankNum:DESC,updatedAt:DESC", page, size)
        val result = service.selectAllByParentId("", pageable)
        return ResponsePage(result.content, result.totalElements.toInt())
    }

    fun sublist(id: String, page: Int, size: Int): ResponsePage<ProductMenuEntity> {
        val pageable = SpringBootUtil.dbMongoPageable("rankNum:DESC,updatedAt:DESC", page, size)
        val result = service.selectAllByParentId(id, pageable)
        return ResponsePage(result.content, result.totalElements.toInt())
    }

    fun store(dto: ProductMenuDTO) = service.save(dto.entity())

    fun update(id: String, dto: ProductMenuDTO) = service.save(dto.entity(findById(id)))

    fun destroy(id: String): String {
        service.deleteById(id)
        return ResponseUtil.SUCCESS
    }

}