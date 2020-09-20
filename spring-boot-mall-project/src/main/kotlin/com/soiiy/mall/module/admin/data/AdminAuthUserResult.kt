package com.soiiy.mall.module.admin.data

import com.soiiy.security.SecurityTarget

open class AdminAuthUserResult: SecurityTarget {

    var id: String = ""

    // 管理员角色
    var role: String = "NORMAL"

    var name: String = ""

    var avatarUrl: String? = null

    var sex: String = "NONE"

    // 不能重复
    var username: String = ""

    var password: String = ""

    // NORMAL: 正常 - LOCKED: 锁定账号（无法登陆）
    var limitState: String = "NORMAL"

    override fun principal(): String = id

    override fun roles(): List<String> = listOf(role)

    override fun permissions(): List<String> = listOf()

}