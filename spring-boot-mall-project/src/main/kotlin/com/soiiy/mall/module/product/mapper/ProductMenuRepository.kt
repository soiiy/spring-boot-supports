package com.soiiy.mall.module.product.mapper

import com.soiiy.mall.module.product.data.ProductMenuEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ProductMenuRepository: MongoRepository<ProductMenuEntity, String> {

    @Query("{parentId: ?0}")
    fun selectAllByParentId(id: String, pageable: Pageable): Page<ProductMenuEntity>

}