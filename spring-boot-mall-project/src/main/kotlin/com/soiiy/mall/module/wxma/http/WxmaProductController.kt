package com.soiiy.mall.module.wxma.http

import com.soiiy.mall.module.product.data.ProductItemResult
import com.soiiy.mall.module.product.service.ProductItemService
import com.soiiy.utils.http.ResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wxma-prods")
class WxmaProductController {

    @Autowired
    lateinit var service: ProductItemService

    @GetMapping
    fun index(
        @RequestParam(required = false) menu: String?,
        @RequestParam(required = false) topic: String?,
        @RequestParam(required = false) keywords: String?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponsePage<ProductItemResult> = service.index("NORMAL", menu, topic, keywords, page, size)

    @GetMapping("/{id}")
    fun show(@PathVariable id: String): ProductItemResult = service.show(id)

}