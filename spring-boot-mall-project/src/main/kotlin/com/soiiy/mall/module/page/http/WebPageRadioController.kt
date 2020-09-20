package com.soiiy.mall.module.page.http

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/page-radio")
class WebPageRadioController {

    @GetMapping
    fun index(): ModelAndView = ModelAndView("page/radio_index")

    @GetMapping("/create")
    fun create(): ModelAndView = ModelAndView("page/radio_create")

    @GetMapping("/{id}/edit")
    fun edit(@PathVariable("id") id: String): ModelAndView = ModelAndView("page/radio_edit", "id", id)

}