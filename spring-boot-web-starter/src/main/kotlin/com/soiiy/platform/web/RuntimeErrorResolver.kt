package com.soiiy.platform.web

import com.soiiy.platform.utils.error.RuntimeError
import com.soiiy.platform.utils.http.ResponseError
import com.soiiy.platform.utils.http.ResponseUtil
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class RuntimeErrorResolver {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeError::class)
    fun service(e: Throwable): ResponseError {
        e.printStackTrace()
        return ResponseUtil.fail(e)
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(Throwable::class)
    fun runtime(e: Throwable): ResponseError {
        e.printStackTrace()
        return ResponseUtil.fail(e)
    }

}