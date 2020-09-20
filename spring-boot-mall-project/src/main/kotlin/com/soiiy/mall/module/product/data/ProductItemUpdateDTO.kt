package com.soiiy.mall.module.product.data

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.soiiy.utils.http.ShareRequest
import com.soiiy.utils.json.Jackson

class ProductItemUpdateDTO: ShareRequest<ProductItemEntity> {

    var itemMenu: String = ""

    var itemTopic: String? = null

    // 自定义商品编号
    var customNo: String? = null

    //标题
    var title: String = ""

    //商品详情信息
    var content: String? = null

    //主图
    var firstPicUrl: String? = null

    //商品图片
    var albumPicUrls: String? = null

    //价格
    @JsonSerialize(using = Jackson.Decimal2Serializer::class)
    @JsonDeserialize(using = Jackson.Decimal2Deserializer::class)
    var price: Number = 0

    @JsonSerialize(using = Jackson.Decimal2Serializer::class)
    @JsonDeserialize(using = Jackson.Decimal2Deserializer::class)
    var originalPrice: Number = 0

    var rankNum: Int = 0

    var extraTags: String? = null


}