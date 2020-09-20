package com.soiiy.mall.module.page.service

import com.soiiy.mall.module.page.data.PageBannerDTO
import com.soiiy.mall.module.page.data.PageBannerEntity
import com.soiiy.mall.module.page.mapper.PageBannerRepository
import com.soiiy.mall.support.SpringBootUtil
import com.soiiy.utils.error.ArgumentInvalidError
import com.soiiy.utils.http.ResponsePage
import com.soiiy.utils.http.ResponseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PageBannerService {

    @Autowired
    lateinit var service: PageBannerRepository

    fun findById(id: String): PageBannerEntity = service.findById(id).orElseThrow { ArgumentInvalidError("数据不存在！") }

    fun index(keywords: String?, page: Int, size: Int): ResponsePage<PageBannerEntity> {
        val pageable = SpringBootUtil.dbMongoPageable("rankNum:DESC,updatedAt:DESC", page, size)
        val result = service.selectAllByKeywords(keywords.orEmpty(), pageable)
        return ResponsePage(result.content, result.totalElements)
    }

    fun store(dto: PageBannerDTO) = service.save(dto.entity())

    fun update(id: String, dto: PageBannerDTO) = service.save(dto.entity(findById(id)))

    fun destroy(id: String): String {
        service.deleteById(id)
        return ResponseUtil.SUCCESS
    }

}