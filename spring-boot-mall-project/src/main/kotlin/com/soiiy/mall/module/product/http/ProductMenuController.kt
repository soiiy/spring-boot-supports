package com.soiiy.mall.module.product.http

import com.soiiy.mall.module.product.data.ProductMenuDTO
import com.soiiy.mall.module.product.data.ProductMenuEntity
import com.soiiy.mall.module.product.data.ProductMenuQuery
import com.soiiy.mall.module.product.service.ProductMenuService
import com.soiiy.utils.http.ResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/product-menus")
class ProductMenuController {

    @Autowired
    lateinit var service: ProductMenuService

    @GetMapping
    fun index(
        @RequestParam(required = false) keywords: String?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponsePage<ProductMenuEntity> = service.index(page, size)

    @GetMapping("/{id}")
    fun show(@PathVariable id: String): ProductMenuEntity = service.findById(id)

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: String): ProductMenuQuery = ProductMenuQuery(show(id))

    @PostMapping
    fun store(@RequestBody dto: ProductMenuDTO): ProductMenuEntity = service.store(dto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody dto: ProductMenuDTO): ProductMenuEntity = service.update(id, dto)

    @GetMapping("/{id}/sublist")
    fun sublist(
        @PathVariable id: String,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponsePage<ProductMenuEntity> = service.sublist(id, page, size)

    @DeleteMapping("/{id}")
    fun destroy(@PathVariable id: String): String = service.destroy(id)



}