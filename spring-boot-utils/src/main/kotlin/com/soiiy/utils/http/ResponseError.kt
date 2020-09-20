package com.soiiy.utils.http

import com.soiiy.utils.error.RuntimeError

data class ResponseError (

    var errCode: Int = -1,

    var errMsg: String = "UNKNOWN ERROR !",

    var errDetail: Any? = null

) {

    constructor(e: RuntimeError): this(e.code, e.message, e.detail)

}