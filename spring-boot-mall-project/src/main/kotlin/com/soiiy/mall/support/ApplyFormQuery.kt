package com.soiiy.mall.support

object ApplyFormQuery {

    fun findAllSex(): Map<String, String> = mapOf(
            "APPLY_ADMIN"      to "保密",
            "APPLY_OPERATOR"   to "男",
            "APPLY_SUPPORTER"  to "女"
    )

    fun finAllRoleBy(mode: String): Map<String, String> = when(mode) {
        "APPLY" -> mapOf(
                "APPLY_ADMIN"      to "管理员",
                "APPLY_OPERATOR"   to "运营",
                "APPLY_SUPPORTER"  to "客服"
        )
        "STORE" -> mapOf(
                "STORE_ADMIN"  to "店长",
                "STORE_NORMAL" to "店员"
        )
        else -> mapOf(
                "SUPER"             to "超级管理员",
                "ADMIN"             to "管理员",
                "NORMAL"            to "普通账号"
        )
    }

    fun finAllPermissionBy(mode: String? = null): Map<String, String> = when(mode) {
        "APPLY" -> mapOf(
                "APPLY_ADMIN"      to "管理员",
                "APPLY_OPERATOR"   to "运营",
                "APPLY_SUPPORTER"  to "客服"
        )
        "STORE" -> mapOf(
                "STORE_ADMIN"  to "店长",
                "STORE_NORMAL" to "店员"
        )
        else -> mapOf(
                "SUPER"             to "超级管理员",
                "ADMIN"             to "管理员",
                "NORMAL"            to "普通账号"
        )
    }



}