package com.soiiy.mall.module.wxma.data

import com.soiiy.mall.module.product.data.ProductItemResult
import com.soiiy.mall.module.product.data.ProductMenuEntity
import com.soiiy.mall.module.product.data.ProductTopicEntity

data class WxmaMenuResult (
    var action: ProductMenuEntity?,
    var prods: List<ProductItemResult>,
    var menus: List<ProductMenuEntity> = listOf()
)