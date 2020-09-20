package com.soiiy.mall.module.admin.data

import com.soiiy.utils.http.ShareRequest
import org.hibernate.validator.constraints.Length
import java.io.Serializable
import javax.validation.constraints.NotEmpty

class AdminUserUpdateDTO: ShareRequest<AdminUserEntity>, Serializable {

    @NotEmpty(message = "用户姓名不能为空！")
    var name: String = ""

    var avatarUrl: String? = null

    var sex: String = "NONE"

    @Length(min = 4, max = 16, message = "账号必须是4-16位字符串！")
    var username: String = ""

}