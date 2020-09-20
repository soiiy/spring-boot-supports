package com.soiiy.mall.module.page.data

import com.soiiy.utils.http.ShareRequest

open class PageRadioDTO: ShareRequest<PageRadioEntity> {

    var title: String = ""

    var content: String = ""

    // 地址 - id
    var target: String = ""

}