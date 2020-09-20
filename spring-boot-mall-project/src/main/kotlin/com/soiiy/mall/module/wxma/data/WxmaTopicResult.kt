package com.soiiy.mall.module.wxma.data

import com.soiiy.mall.module.product.data.ProductItemResult
import com.soiiy.mall.module.product.data.ProductTopicEntity

data class WxmaTopicResult (
    var topic: ProductTopicEntity,
    var prods: List<ProductItemResult>
)