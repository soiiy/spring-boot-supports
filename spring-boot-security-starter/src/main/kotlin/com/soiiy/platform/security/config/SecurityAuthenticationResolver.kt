package com.soiiy.platform.security.config

import com.soiiy.platform.security.annotation.SecurityUser
import com.soiiy.platform.security.error.UnAuthenticationError
import com.soiiy.platform.security.support.SecurityContextHolder
import org.springframework.core.MethodParameter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver

/**
 * 登陆参数转换
 */
class SecurityAuthenticationResolver(converters: List<HttpMessageConverter<*>>): AbstractMessageConverterMethodArgumentResolver(converters) {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(SecurityUser::class.java)
//        return SecurityTarget::class.java.isAssignableFrom(parameter.parameterType)
    }

    override fun resolveArgument(parameter: MethodParameter, p1: ModelAndViewContainer?, p2: NativeWebRequest, p3: WebDataBinderFactory?): Any? {
        val principal = SecurityContextHolder.get()
        if (principal === null) throw UnAuthenticationError()
        return SecurityContextHolder.get()
    }

}