package com.soiiy.security.config

import com.soiiy.security.error.UnAuthenticationError
import com.soiiy.security.error.UnAuthorizationError
import com.soiiy.security.support.SecurityContextProvider
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class SecurityExceptionResolver {

    @ExceptionHandler(UnAuthorizationError::class)
    fun authorize(e: UnAuthorizationError) = SecurityContextProvider.failure(e)

    @ExceptionHandler(UnAuthenticationError::class)
    fun authenticate(e: UnAuthenticationError) = SecurityContextProvider.failure(e)

}