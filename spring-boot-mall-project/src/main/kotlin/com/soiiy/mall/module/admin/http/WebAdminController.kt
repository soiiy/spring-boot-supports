package com.soiiy.mall.module.admin.http

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
class WebAdminController {

    @GetMapping
    fun index(): ModelAndView = ModelAndView("admin/index")

}