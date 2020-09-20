package com.soiiy.utils.http

import com.soiiy.utils.error.RuntimeError

object ResponseUtil {

    const val SUCCESS: String = "SUCCESS"

    fun fail(e: Throwable): ResponseError = when (e) {
        is RuntimeError -> ResponseError(e)
        else -> ResponseError(errDetail = e.message)
    }

    fun <E> page(data: List<E>,total: Number): ResponsePage<E> {
        return ResponsePage(data, total.toInt())
    }

    fun <E> gateway(data: Any?): ResponseGateway =
        ResponseGateway(data)

}