package com.soiiy.mall.module.page.http

import com.soiiy.mall.module.page.data.PageMenuDTO
import com.soiiy.mall.module.page.data.PageMenuEntity
import com.soiiy.mall.module.page.data.PageMenuQuery
import com.soiiy.mall.module.page.service.PageMenuService
import com.soiiy.utils.http.ResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/page-menus")
class PageMenuController {

    @Autowired
    lateinit var service: PageMenuService

    @GetMapping
    fun index(
        @RequestParam(required = false) keywords: String?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponsePage<PageMenuEntity> = service.index(keywords, page, size)

    @GetMapping("/{id}")
    fun show(@PathVariable id: String): PageMenuEntity = service.findById(id)

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: String): PageMenuQuery = PageMenuQuery(show(id))

    @PostMapping
    fun store(@RequestBody dto: PageMenuDTO): PageMenuEntity = service.store(dto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody dto: PageMenuDTO): PageMenuEntity = service.update(id, dto)

    @DeleteMapping("/{id}")
    fun destroy(@PathVariable id: String): String = service.destroy(id)

}