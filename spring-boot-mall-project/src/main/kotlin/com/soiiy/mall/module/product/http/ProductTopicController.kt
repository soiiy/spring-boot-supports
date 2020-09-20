package com.soiiy.mall.module.product.http

import com.soiiy.mall.module.product.data.ProductTopicDTO
import com.soiiy.mall.module.product.data.ProductTopicEntity
import com.soiiy.mall.module.product.data.ProductTopicQuery
import com.soiiy.mall.module.product.service.ProductTopicService
import com.soiiy.utils.http.ResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/product-topics")
class ProductTopicController {

    @Autowired
    lateinit var service: ProductTopicService

    @GetMapping
    fun index(
        @RequestParam(required = false) keywords: String?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponsePage<ProductTopicEntity> = service.index(keywords, page, size)

    @GetMapping("/{id}")
    fun show(@PathVariable id: String): ProductTopicEntity = service.findById(id)

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: String): ProductTopicQuery = ProductTopicQuery(show(id))

    @PostMapping
    fun store(@RequestBody dto: ProductTopicDTO): ProductTopicEntity = service.store(dto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody dto: ProductTopicDTO): ProductTopicEntity = service.update(id, dto)

    @DeleteMapping("/{id}")
    fun destroy(@PathVariable id: String): String = service.destroy(id)

}