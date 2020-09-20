package com.soiiy.mall.module.admin.service

import com.soiiy.mall.module.admin.data.AdminAuthLoginDTO
import com.soiiy.mall.module.admin.data.AdminAuthLoginResult
import com.soiiy.security.SecurityManager
import com.soiiy.utils.error.ArgumentInvalidError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminAuthService {

    @Autowired
    lateinit var user: AdminUserService

    @Autowired
    lateinit var manager: SecurityManager

    fun login(dto: AdminAuthLoginDTO): AdminAuthLoginResult {
        val principal = user.findByUsername(dto.username).result()
        if (principal.password != dto.password) throw ArgumentInvalidError("密码错误！")
        val token = manager.save(principal)
        return AdminAuthLoginResult(token, principal)
    }

    fun logout(token: String): String {
        manager.remove(token)
        return "SUCCESS!"
    }

}