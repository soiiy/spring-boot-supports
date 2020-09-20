package com.soiiy.mall.module.admin.data

data class AdminAuthLoginResult(
        var token: String,
        var info: AdminAuthUserResult
)