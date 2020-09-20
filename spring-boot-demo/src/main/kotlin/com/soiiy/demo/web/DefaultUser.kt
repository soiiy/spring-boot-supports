package com.soiiy.demo.web

import com.soiiy.security.SecurityTarget

class DefaultUser(
    var id: String = "",
    var name: String = ""
): SecurityTarget {

    override fun principal(): String = id

    override fun roles(): List<String> = listOf("admin", "user")

    override fun permissions(): List<String> = listOf("user_create", "user_edit")

}