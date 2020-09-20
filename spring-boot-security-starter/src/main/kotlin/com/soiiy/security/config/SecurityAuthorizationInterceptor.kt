package com.soiiy.security.config

import com.soiiy.security.SecurityTarget
import com.soiiy.security.annotation.SecurityAny
import com.soiiy.security.annotation.SecurityOnly
import com.soiiy.security.error.UnAuthorizationError
import com.soiiy.security.support.SecurityContextHolder
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SecurityAuthorizationInterceptor: HandlerInterceptor {

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        modelAndView?.addObject("security", SecurityContextHolder.get())
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        val next = if (handler is HandlerMethod) {
            val principal = SecurityContextHolder.get()
            when {
                handler.hasMethodAnnotation(SecurityAny::class.java) -> {
                    handleAny(principal, handler.method.getAnnotation(SecurityAny::class.java))
                }
                handler.hasMethodAnnotation(SecurityOnly::class.java) -> {
                    handleOnly(principal, handler.method.getAnnotation(SecurityOnly::class.java))
                }
                else -> true
            }
        } else true

        if (!next) throw UnAuthorizationError()

        return next

    }

    private fun handleItemAny(items: List<String>, limits: List<String>): Boolean {
        if (limits.isNullOrEmpty()) return false
        return items.any { limits.contains(it) }
    }

    private fun handleItemOnly(items: List<String>, limits: List<String>): Boolean {
        if (limits.isNullOrEmpty()) return true
        return items.containsAll(limits)
    }

    private fun handleAny(principal: SecurityTarget?, security: SecurityAny): Boolean {
        if (principal === null) return false
        if (security.roles.isNullOrEmpty() && security.permissions.isNullOrEmpty()) {
            return true
        }
        return handleItemAny(principal.roles(), security.roles.toList()) ||
                handleItemAny(principal.permissions(), security.permissions.toList())
    }

    private fun handleOnly(principal: SecurityTarget?, security: SecurityOnly): Boolean {
        if (principal === null) return false
        return handleItemOnly(principal.roles(), security.roles.toList()) &&
                handleItemOnly(principal.permissions(), security.permissions.toList())
    }

}