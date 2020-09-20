package com.soiiy.mall.module.admin.http

import com.hupoll.platform.util.json.JsonUtil
import com.soiiy.mall.module.admin.data.AdminAuthUserResult
import com.soiiy.security.annotation.SecurityUser
import com.soiiy.upload.UploadService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/admin-upload")
class AdminUploadController {

    @Autowired
    lateinit var upload: UploadService

    @PostMapping
    fun store(@SecurityUser auth: AdminAuthUserResult, @RequestParam file: MultipartFile): String {
        println(JsonUtil.toJSONString(auth))
        return upload.store("user_${auth.id}", file.inputStream)
    }

}