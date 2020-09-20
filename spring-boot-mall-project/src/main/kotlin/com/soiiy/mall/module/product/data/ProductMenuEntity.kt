package com.soiiy.mall.module.product.data

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "product_menus")
class ProductMenuEntity {

    @Id
    var id: String = ObjectId.get().toHexString()

    var parentId: String = ""

    // 标题
    var name: String = ""

    // 主图
    var firstPicUrl: String? = null

    // 轮播图片
    var albumPicUrls: String? = null

    var rankNum: Int = 0

    var createdAt: LocalDateTime = LocalDateTime.now()

    var updatedAt: LocalDateTime = LocalDateTime.now()

}