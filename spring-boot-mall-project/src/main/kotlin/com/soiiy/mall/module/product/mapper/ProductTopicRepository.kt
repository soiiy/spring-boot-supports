package com.soiiy.mall.module.product.mapper

import com.soiiy.mall.module.product.data.ProductTopicEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ProductTopicRepository: MongoRepository<ProductTopicEntity, String> {

    @Query("{name: {\$regex : ?0}}")
    fun selectAllByKeywords(keywords: String, pageable: Pageable): Page<ProductTopicEntity>

}