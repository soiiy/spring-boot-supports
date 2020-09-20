package com.soiiy.mall.module.page.mapper

import com.soiiy.mall.module.page.data.PageRadioEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface PageRadioRepository: MongoRepository<PageRadioEntity, String> {

    @Query("{title: {\$regex : ?0}}")
    fun selectAllByKeywords(keywords: String, pageable: Pageable): Page<PageRadioEntity>

}