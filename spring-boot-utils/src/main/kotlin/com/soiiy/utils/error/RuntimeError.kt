package com.soiiy.utils.error

abstract class RuntimeError(

    // 错误码
    val code: Int,

    // 错误信息
    override val message: String,

    // 错误详情 - 堆栈
    val detail: Any? = null


): Exception()