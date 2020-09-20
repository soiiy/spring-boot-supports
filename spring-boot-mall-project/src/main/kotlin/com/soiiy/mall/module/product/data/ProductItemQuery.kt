package com.soiiy.mall.module.product.data

data class ProductItemQuery (
    var formData: ProductItemResult? = null
) {

    var formMenus: String = "page:/product-menus"

    var formTopics: String = "page:/product-topics"

    var formSkuModes = mapOf(
        "ITEM" to "单品",
        "LIST" to "多规格"
    )

    var formSellStates = mapOf(
        "SELL_UP" to "销售中",
        "SELL_DOWN" to "下架",
        "SELL_OUT" to "售罄"
    )

}