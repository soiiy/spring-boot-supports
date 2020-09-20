package com.soiiy.mall.module.product.http

import com.soiiy.mall.module.product.data.*
import com.soiiy.mall.module.product.service.ProductItemService
import com.soiiy.utils.http.ResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/product-items")
class ProductItemController {

    @Autowired
    lateinit var service: ProductItemService

    @GetMapping("/query")
    fun query(): ProductItemQuery = service.query()

    @GetMapping
    fun index(
        @RequestParam(required = false) state: String?,
        @RequestParam(required = false) menu: String?,
        @RequestParam(required = false) topic: String?,
        @RequestParam(required = false) keywords: String?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponsePage<ProductItemResult> = service.index(state, menu, topic, keywords, page, size)

    @GetMapping("/{id}")
    fun show(@PathVariable id: String): ProductItemResult = service.show(id)

    @GetMapping("/create")
    fun create(): ProductItemQuery = ProductItemQuery()

    @PostMapping
    fun store(@RequestBody dto: ProductItemCreateDTO): ProductItemEntity = service.store(dto)

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable id: String): ProductItemQuery = ProductItemQuery(show(id))

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody dto: ProductItemUpdateDTO): ProductItemEntity = service.update(id, dto)

    @PutMapping("/{id}/sellDown")
    fun sellDown(@PathVariable id: String): ProductItemEntity = service.updateBySellState(id, "SELL_DOWN")

    @PutMapping("/{id}/sellUp")
    fun sellUp(@PathVariable id: String): ProductItemEntity = service.updateBySellState(id, "NORMAL")


}