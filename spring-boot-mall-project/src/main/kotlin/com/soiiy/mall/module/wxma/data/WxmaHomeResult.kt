package com.soiiy.mall.module.wxma.data

import com.soiiy.mall.module.page.data.PageBannerEntity
import com.soiiy.mall.module.page.data.PageMenuEntity
import com.soiiy.mall.module.page.data.PageRadioEntity

data class WxmaHomeResult(
    var menus: List<PageMenuEntity>,
    var banners: List<PageBannerEntity>,
    var topics: List<WxmaTopicResult>,
    var radio: PageRadioEntity? = null
)