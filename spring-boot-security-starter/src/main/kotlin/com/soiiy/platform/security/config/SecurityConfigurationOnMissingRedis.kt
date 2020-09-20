package com.soiiy.platform.security.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class SecurityConfigurationOnMissingRedis: WebMvcConfigurer {

//    @Bean
//    @ConditionalOnMissingBean(RedisConnectionFactory::class)
//    open fun redisConnectionFactory(): LettuceConnectionFactory = LettuceConnectionFactory()
//
//    @Bean
//    @ConditionalOnMissingBean(name = ["redisTemplate"])
//    open fun redisTemplate(redisConnectionFactory: RedisConnectionFactory?): RedisTemplate<Any, Any>? {
//        val template = RedisTemplate<Any, Any>()
//        template.setConnectionFactory(redisConnectionFactory!!)
//        return template
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    open fun stringRedisTemplate(redisConnectionFactory: RedisConnectionFactory?): StringRedisTemplate? {
//        val template = StringRedisTemplate()
//        template.setConnectionFactory(redisConnectionFactory!!)
//        return template
//    }

}