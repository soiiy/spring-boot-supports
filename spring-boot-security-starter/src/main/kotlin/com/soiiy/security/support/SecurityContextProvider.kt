package com.soiiy.security.support

import com.soiiy.security.provider.SecurityProvider
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object SecurityContextProvider {

    private val context = ThreadLocal<SecurityProvider>()

    private val requestContext = ThreadLocal<HttpServletRequest>()

    private val responseContext = ThreadLocal<HttpServletResponse>()

    fun set(provider: SecurityProvider): SecurityContextProvider {
        context.set(provider)
        return this
    }

    fun set(request: HttpServletRequest, response: HttpServletResponse): SecurityContextProvider {
        requestContext.set(request)
        responseContext.set(response)
        return this
    }

    fun support(providers: List<SecurityProvider>): SecurityContextProvider {
        return set(providers.sortedBy { it.order() }.first { it.support(request()) })
    }

    fun get(): SecurityProvider = context.get()

    fun request(): HttpServletRequest = requestContext.get()

    fun response(): HttpServletResponse = responseContext.get()

    fun clear(): SecurityContextProvider {
        requestContext.remove()
        responseContext.remove()
        context.remove()
        return this
    }

    fun principal(): String? = get().principal(request())

    fun failure(error: Throwable) = get().failure(error, response())

}