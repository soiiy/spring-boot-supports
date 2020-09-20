package com.soiiy.platform.security.config

import com.soiiy.platform.security.SecurityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Role
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class SecurityWebMvcConfiguration: WebMvcConfigurer {

    @Autowired
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    lateinit var messageConverters: HttpMessageConverters

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    open fun registFilter(manager: _root_ide_package_.com.soiiy.platform.security.SecurityManager): FilterRegistrationBean<SecurityAuthenticationFilter> {
        val filter = SecurityAuthenticationFilter(manager)
        val registration = FilterRegistrationBean(filter)
        registration.addUrlPatterns("/*")
        registration.setName("AuthenticationFilter")
        registration.order = 1
        return registration
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(SecurityAuthorizationInterceptor())
            .addPathPatterns("/**")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(SecurityAuthenticationResolver(messageConverters.converters))
    }

}