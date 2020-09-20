package com.soiiy.mall.module.wxma.http

import com.soiiy.mall.module.product.data.ProductMenuEntity
import com.soiiy.mall.module.product.service.ProductMenuService
import com.soiiy.utils.http.ResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wxma-menus")
class WxmaMenuController {

    @Autowired
    lateinit var menu: ProductMenuService

    @GetMapping
    fun index(): ResponsePage<ProductMenuEntity> = menu.index(1, 10)


}