package com.soiiy.mall.module.admin.mapper

import com.soiiy.mall.module.admin.data.AdminUserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface AdminUserRepository: MongoRepository<AdminUserEntity, String> {

    fun existsByUsername(username: String): Boolean

    fun findByUsername(username: String): AdminUserEntity?

    @Query("{\$or: {name: {\$regex : ?0}, mobile: {\$regex: ?0}}}")
    fun selectAllByKeywords(keywords: String?, pageable: Pageable): Page<AdminUserEntity>

}