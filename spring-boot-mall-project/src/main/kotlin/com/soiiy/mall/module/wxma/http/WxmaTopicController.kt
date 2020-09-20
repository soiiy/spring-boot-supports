package com.soiiy.mall.module.wxma.http

import com.soiiy.mall.module.product.data.ProductTopicEntity
import com.soiiy.mall.module.product.service.ProductTopicService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/wxma-topics")
class WxmaTopicController {

    @Autowired
    lateinit var service: ProductTopicService

    @GetMapping("/{id}")
    fun show(@PathVariable id: String): ProductTopicEntity = service.findById(id)

}