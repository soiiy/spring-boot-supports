package com.soiiy.mall.module.product.http

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/product-topic")
class WebTopicController {

    @GetMapping
    fun index(): ModelAndView = ModelAndView("product/topic_index")

    @GetMapping("/create")
    fun create(): ModelAndView = ModelAndView("product/topic_create")

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable("id") id: String): ModelAndView = ModelAndView("product/topic_edit", "id", id)

}