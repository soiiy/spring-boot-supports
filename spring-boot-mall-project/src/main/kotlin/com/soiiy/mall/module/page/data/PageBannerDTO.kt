package com.soiiy.mall.module.page.data

import com.soiiy.utils.http.ShareRequest

class PageBannerDTO: ShareRequest<PageBannerEntity> {

    var name: String = ""

    var picUrl: String? = null

    var target: String = ""

    var rankNum: Int = 0

}