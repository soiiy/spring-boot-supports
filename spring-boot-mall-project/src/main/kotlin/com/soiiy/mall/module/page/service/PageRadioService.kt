package com.soiiy.mall.module.page.service

import com.soiiy.mall.module.page.data.PageBannerDTO
import com.soiiy.mall.module.page.data.PageBannerEntity
import com.soiiy.mall.module.page.data.PageRadioDTO
import com.soiiy.mall.module.page.data.PageRadioEntity
import com.soiiy.mall.module.page.mapper.PageBannerRepository
import com.soiiy.mall.module.page.mapper.PageRadioRepository
import com.soiiy.mall.support.SpringBootUtil
import com.soiiy.utils.error.ArgumentInvalidError
import com.soiiy.utils.http.ResponsePage
import com.soiiy.utils.http.ResponseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PageRadioService {

    @Autowired
    lateinit var service: PageRadioRepository

    fun findById(id: String): PageRadioEntity = service.findById(id).orElseThrow { ArgumentInvalidError("数据不存在！") }

    fun index(keywords: String?, page: Int, size: Int): ResponsePage<PageRadioEntity> {
        val pageable = SpringBootUtil.dbMongoPageable("createdAt:DESC", page, size)
        val result = service.selectAllByKeywords(keywords.orEmpty(), pageable)
        return ResponsePage(result.content, result.totalElements)
    }

    fun store(dto: PageRadioDTO): PageRadioEntity = service.save(dto.entity())

    fun update(id: String, dto: PageRadioDTO): PageRadioEntity = service.save(dto.entity(findById(id)))

    fun destroy(id: String): String {
        service.deleteById(id)
        return ResponseUtil.SUCCESS
    }

}