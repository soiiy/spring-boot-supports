package com.soiiy.platform.utils.http

data class ResponseGateway (

    var data: Any? = null

) {

    var errCode: Int = 0

    var errMsg: String = "SUCCESS"

}