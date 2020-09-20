package com.soiiy.mall.module.admin.data

import com.soiiy.mall.support.ApplyFormQuery

class AccountUserQuery {

    var formData: Any? = null

    // 店铺角色， 网点角色

    // 选择应用
    var applyFormUri: String = "list:url:Keywords"

    var storeFormUri: String = ""

    var createFormTime: String = ""

    var sexOptions: Map<String,String> = ApplyFormQuery.findAllSex()

    var roleOptions: Map<String,String> = mapOf()

    var sortedByOptions: Map<String, String> = mapOf()

    var limitStateOptions: Map<String, String> = mapOf()

}