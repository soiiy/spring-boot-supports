package com.soiiy.mall.module.product.http

import com.soiiy.mall.module.product.data.ProductItemQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/product-item")
class WebItemController {

    @GetMapping
    fun index(): ModelAndView = ModelAndView("product/item_index")

    @GetMapping("/create")
    fun create(): ModelAndView = ModelAndView("product/item_create")

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable("id") id: String): ModelAndView = ModelAndView("product/item_edit", "id", id)

}