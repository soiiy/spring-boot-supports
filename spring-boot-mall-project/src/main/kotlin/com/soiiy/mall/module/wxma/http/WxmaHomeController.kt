package com.soiiy.mall.module.wxma.http

import com.soiiy.mall.module.page.service.PageBannerService
import com.soiiy.mall.module.page.service.PageMenuService
import com.soiiy.mall.module.page.service.PageRadioService
import com.soiiy.mall.module.product.service.ProductItemService
import com.soiiy.mall.module.product.service.ProductTopicService
import com.soiiy.mall.module.wxma.data.WxmaHomeResult
import com.soiiy.mall.module.wxma.data.WxmaTopicResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wxma-home")
class WxmaHomeController {

    @Autowired
    lateinit var menu: PageMenuService

    @Autowired
    lateinit var banner: PageBannerService

    @Autowired
    lateinit var radio: PageRadioService

    @Autowired
    lateinit var topic: ProductTopicService

    @Autowired
    lateinit var item: ProductItemService


    @GetMapping
    fun index(): WxmaHomeResult {
        val radioResult = radio.index(null, 1, 1).data.firstOrNull()
        val menuResult = menu.index(null, 1, 8).data
        val bannerResult = banner.index(null, 1, 5).data
        val topics = topic.index(null, 1, 3).data

        val topicResult = topics.map {
            WxmaTopicResult(it, item.index(
                null, null, it.id, null, 1, 3
            ).data)
        }
        return WxmaHomeResult(menuResult, bannerResult, topicResult, radioResult)
    }

}