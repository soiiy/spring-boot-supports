package com.soiiy.security.config

import com.soiiy.security.SecurityManager
import com.soiiy.security.provider.SecurityAjaxProvider
import com.soiiy.security.provider.SecurityJsonProvider
import com.soiiy.security.provider.SecurityWebProvider
import com.soiiy.security.storage.SecurityStorageEngine
import com.soiiy.security.storage.SecurityStorageInMemory
import com.soiiy.security.storage.SecurityStorageInRedis
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Role
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableConfigurationProperties(SecurityContextProperty::class)
open class SecurityContextConfiguration: WebMvcConfigurer {

    @Autowired
    lateinit var property: SecurityContextProperty

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    open fun manager(storage: SecurityStorageEngine): SecurityManager {
        val model = SecurityManager(storage)
        afterManagerInitial(model)
        return model
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    open fun storage(): SecurityStorageEngine {
        if (property.mode.toUpperCase() == "REDIS") {
            return SecurityStorageInRedis(property)
        }
        return SecurityStorageInMemory(property)
    }

    private fun afterManagerInitial(manager: SecurityManager) {
        manager.target = property.target
        manager.permitUrls = property.permitUrls + property.redirect401
        manager.providers = listOf(
            SecurityAjaxProvider(property),
            SecurityJsonProvider(property),
            SecurityWebProvider(property)
        )
    }



}