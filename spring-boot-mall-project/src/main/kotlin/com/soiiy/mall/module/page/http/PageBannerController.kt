package com.soiiy.mall.module.page.http

import com.soiiy.mall.module.page.data.PageBannerDTO
import com.soiiy.mall.module.page.data.PageBannerEntity
import com.soiiy.mall.module.page.data.PageBannerQuery
import com.soiiy.mall.module.page.service.PageBannerService
import com.soiiy.utils.http.ResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/page-banners")
class PageBannerController {

    @Autowired
    lateinit var service: PageBannerService

    @GetMapping
    fun index(
        @RequestParam(required = false) keywords: String?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponsePage<PageBannerEntity> = service.index(keywords, page, size)

    @GetMapping("/{id}")
    fun show(@PathVariable id: String): PageBannerEntity = service.findById(id)

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: String): PageBannerQuery = PageBannerQuery(show(id))

    @PostMapping
    fun store(@RequestBody dto: PageBannerDTO): PageBannerEntity = service.store(dto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody dto: PageBannerDTO): PageBannerEntity = service.update(id, dto)

    @DeleteMapping("/{id}")
    fun destroy(@PathVariable id: String): String = service.destroy(id)

}