package com.soiiy.platform.security.config

import com.soiiy.platform.security.SecurityManager
import com.soiiy.platform.security.support.SecurityContextHolder
import com.soiiy.platform.security.support.SecurityContextProvider
import com.soiiy.platform.utils.error.RuntimeError
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SecurityAuthenticationFilter(private val manager: _root_ide_package_.com.soiiy.platform.security.SecurityManager): Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        SecurityContextHolder.clear()
        SecurityContextProvider.clear()
        SecurityContextProvider.set(request as HttpServletRequest, response as HttpServletResponse)
        SecurityContextProvider.support(manager.providers)

        try {

            val principal = manager.principal()
            SecurityContextHolder.set(principal)

            chain.doFilter(request, response)

        } catch (e: RuntimeError) {
            SecurityContextProvider.failure(e)
        }

    }

}