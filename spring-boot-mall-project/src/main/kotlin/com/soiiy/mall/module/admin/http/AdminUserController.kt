package com.soiiy.mall.module.admin.http

import com.soiiy.mall.module.admin.data.AdminUserCreateDTO
import com.soiiy.mall.module.admin.data.AdminUserEntity
import com.soiiy.mall.module.admin.data.AdminUserUpdateDTO
import com.soiiy.mall.module.admin.service.AdminUserService
import com.soiiy.security.SecurityManager
import com.soiiy.utils.http.ResponsePage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/admin-users")
class AdminUserController {

    @Autowired
    lateinit var security: SecurityManager

    @Autowired
    lateinit var service: AdminUserService


//    @GetMapping("/query")
//    fun query(@SecurityUser userModel: AdminAuthUserModel): AccountUserQuery = service.query(userModel.grant)

    @GetMapping
    fun index(
        @RequestParam(required = false) keywords: String?,
        @RequestParam(required = false) sortedBy: String?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponsePage<AdminUserEntity> = service.index(keywords, sortedBy, page, size)

    @GetMapping("/{id}")
    fun show(@PathVariable id: String): AdminUserEntity = service.findById(id)

    @PostMapping
    fun store(@RequestBody dto: AdminUserCreateDTO): AdminUserEntity = service.store(dto)

    @PostMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody dto: AdminUserUpdateDTO): AdminUserEntity = service.update(id, dto)

    @PostMapping("/{id}/password")
    fun updateForPassword(@PathVariable id: String, @RequestBody password: String): AdminUserEntity = service.updateForPassword(id, password)

}