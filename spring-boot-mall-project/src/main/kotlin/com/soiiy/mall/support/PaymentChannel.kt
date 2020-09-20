package com.soiiy.mall.support

interface PaymentChannel {

    fun name(): String

    fun prepay(extraInfo: Map<String, Any>): String

}