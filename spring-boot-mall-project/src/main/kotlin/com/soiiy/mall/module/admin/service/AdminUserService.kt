package com.soiiy.mall.module.admin.service

import com.soiiy.mall.module.admin.data.AdminUserCreateDTO
import com.soiiy.mall.module.admin.data.AdminUserEntity
import com.soiiy.mall.module.admin.data.AdminUserUpdateDTO
import com.soiiy.mall.module.admin.mapper.AdminUserRepository
import com.soiiy.mall.support.SpringBootUtil
import com.soiiy.utils.error.ArgumentUniqueError
import com.soiiy.utils.error.ElementNotFoundError
import com.soiiy.utils.http.ResponsePage
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminUserService {

    @Autowired
    lateinit var repository: AdminUserRepository

    fun index(keywords: String?, sortedBy: String?, page: Int, size: Int): ResponsePage<AdminUserEntity> {
        val pageable = SpringBootUtil.dbMongoPageable(sortedBy, page, size)
        val result = repository.selectAllByKeywords(keywords, pageable)
        return ResponsePage(result.content, result.totalElements.toInt())
    }

    fun findById(id: String): AdminUserEntity {
        return repository.findById(id).orElseThrow{ ElementNotFoundError("不存在的用户ID") }
    }

    fun findByUsername(username: String): AdminUserEntity {
        return repository.findByUsername(username) ?: throw ElementNotFoundError("账号不存在！")
    }

    fun store(dto: AdminUserCreateDTO): AdminUserEntity {
        val entity = dto.entity()
        if (repository.existsByUsername(entity.username)) throw ArgumentUniqueError("当前账号已被使用！")
        return repository.save(entity)
    }

    fun update(id: String, dto: AdminUserUpdateDTO): AdminUserEntity {
        val entity = dto.entity(findById(id))
        if (entity.username != dto.username && repository.existsByUsername(entity.username)) {
            throw ArgumentUniqueError("当前账号已被使用！")
        }
        return repository.save(entity)
    }

    fun updateForPassword(id: String, password: String): AdminUserEntity {
        val entity = findById(id)
        if (password.length < 8 || password.length > 16) {
            throw ArgumentUniqueError("密码必须是8-16位字符串！")
        }
        entity.password = password
        return repository.save(entity)
    }

}