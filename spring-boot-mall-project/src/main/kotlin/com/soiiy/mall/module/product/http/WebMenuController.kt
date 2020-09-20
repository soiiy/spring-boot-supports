package com.soiiy.mall.module.product.http

import com.soiiy.mall.module.product.service.ProductMenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/product-menu")
class WebMenuController {

    @Autowired
    lateinit var service: ProductMenuService

    @GetMapping
    fun index(): ModelAndView = ModelAndView("product/menu_index")

    @GetMapping("/create")
    fun create(): ModelAndView = subcreate("")

    @GetMapping("/{id}/create")
    fun subcreate(@PathVariable id: String): ModelAndView {
        val backUrl = if (id.isEmpty()) "/product-menu" else "/product-menu/${id}/sublist"
        return ModelAndView("product/menu_create",mapOf(
            "id" to id, "backUrl" to backUrl
        ))
    }

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable("id") id: String): ModelAndView {
        val item = service.findById(id)
        val backUrl = if (item.parentId.isEmpty()) "/product-menu" else "/product-menu/${item.parentId}/sublist"
        return ModelAndView("product/menu_edit",mapOf(
            "id" to item.id, "backUrl" to backUrl
        ))
    }

    @GetMapping("/{id}/sublist")
    fun sublist(@PathVariable("id") id: String): ModelAndView {
        val item = service.findById(id)
        return ModelAndView("product/menu_sublist", "item", item)
    }

}