package com.soiiy.mall.module.product.data

import com.soiiy.utils.http.ShareRequest

class ProductMenuDTO: ShareRequest<ProductMenuEntity> {

    var parentId: String = ""

    // 标题
    var name: String = ""

    // 主图
    var firstPicUrl: String? = null

    // 轮播图片
    var albumPicUrls: String? = null

    var rankNum: Int = 0

}