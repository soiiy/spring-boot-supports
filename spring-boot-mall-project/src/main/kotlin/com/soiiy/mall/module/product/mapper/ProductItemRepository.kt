package com.soiiy.mall.module.product.mapper

import com.soiiy.mall.module.product.data.ProductItemEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ProductItemRepository: MongoRepository<ProductItemEntity, String> {

//    @Query("?0")
//    fun selectAllByQuery(query: String, pageable: Pageable): Page<ProductItemEntity>

}