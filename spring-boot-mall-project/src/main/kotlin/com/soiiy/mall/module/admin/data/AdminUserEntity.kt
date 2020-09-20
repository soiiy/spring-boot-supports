package com.soiiy.mall.module.admin.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.soiiy.security.SecurityTarget
import com.soiiy.utils.http.ShareResponse
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "admin_users")
open class AdminUserEntity: SecurityTarget, ShareResponse<AdminAuthUserResult> {

    @Id
    var id: String = ObjectId.get().toHexString()

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updatedAt: LocalDateTime = LocalDateTime.now()

    override fun principal(): String = id

    override fun roles(): List<String> = listOf(role)

    override fun permissions(): List<String> = listOf()

}