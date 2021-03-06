package com.soiiy.mall.module.page.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.soiiy.security.SecurityTarget
import com.soiiy.utils.http.ShareResponse
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "page_menus")
open class PageMenuEntity {

    @Id
    var id: String = ObjectId.get().toHexString()

    var name: String = ""

    var picUrl: String? = null

    // 地址 - id
    var target: String = ""

    var rankNum: Int = 0

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updatedAt: LocalDateTime = LocalDateTime.now()

}