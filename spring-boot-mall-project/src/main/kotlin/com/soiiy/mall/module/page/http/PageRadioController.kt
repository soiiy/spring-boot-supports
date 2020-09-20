package com.soiiy.mall.module.page.http

import com.soiiy.mall.module.page.data.PageRadioDTO
import com.soiiy.mall.module.page.data.PageRadioEntity
import com.soiiy.mall.module.page.data.PageRadioQuery
import com.soiiy.mall.module.page.service.PageRadioService
import com.soiiy.utils.http.ResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/page-radios")
class PageRadioController {

    @Autowired
    lateinit var service: PageRadioService

    @GetMapping
    fun index(
        @RequestParam(required = false) keywords: String?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponsePage<PageRadioEntity> = service.index(keywords, page, size)

    @GetMapping("/{id}")
    fun show(@PathVariable id: String): PageRadioEntity = service.findById(id)

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: String): PageRadioQuery = PageRadioQuery(show(id))

    @PostMapping
    fun store(@RequestBody dto: PageRadioDTO): PageRadioEntity = service.store(dto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody dto: PageRadioDTO): PageRadioEntity = service.update(id, dto)

    @DeleteMapping("/{id}")
    fun destroy(@PathVariable id: String): String = service.destroy(id)

}