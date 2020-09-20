package com.soiiy.mall.module.page.mapper

import com.soiiy.mall.module.page.data.PageBannerEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface PageBannerRepository: MongoRepository<PageBannerEntity, String> {

    @Query("{name: {\$regex : ?0}}")
    fun selectAllByKeywords(keywords: String, pageable: Pageable): Page<PageBannerEntity>

}