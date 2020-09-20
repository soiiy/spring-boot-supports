package com.soiiy.mall.module.page.http

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/page-banner")
class WebPageBannerController {

    @GetMapping
    fun index(): ModelAndView = ModelAndView("page/banner_index")

    @GetMapping("/create")
    fun create(): ModelAndView = ModelAndView("page/banner_create")

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable("id") id: String): ModelAndView = ModelAndView("page/banner_edit", "id", id)

}