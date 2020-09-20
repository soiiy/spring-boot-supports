package com.soiiy.mall.module.page.data

import com.soiiy.utils.http.ShareRequest

open class PageMenuDTO: ShareRequest<PageMenuEntity> {

    var name: String = ""

    var picUrl: String? = null

    // 地址 - id
    var target: String = ""

    var rankNum: Int = 0

}