package com.soiiy.security.storage

import com.soiiy.security.config.SecurityContextProperty
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.beans.factory.SmartInitializingSingleton
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.core.Ordered
import org.springframework.data.redis.core.StringRedisTemplate
import java.util.concurrent.TimeUnit

class SecurityStorageInRedis(
    private val property: SecurityContextProperty
): SecurityStorageEngine, BeanPostProcessor, Ordered, BeanFactoryAware, SmartInitializingSingleton {

    private var beanFactory: BeanFactory? = null

    private var redisTemplate: StringRedisTemplate? = null

    override fun getOrder(): Int = Ordered.LOWEST_PRECEDENCE

    override fun setBeanFactory(beanFactory: BeanFactory) {
        this.beanFactory = beanFactory
    }

    override fun afterSingletonsInstantiated() {
        if (this.beanFactory === null) return
        try {
            this.redisTemplate = this.beanFactory?.getBean(StringRedisTemplate::class.java)
        } catch (e: Exception) { }
    }

    override fun isSingle(): Boolean = property.sso

    override fun expireDay(): Long = property.expireDay

    override fun find(token: String): String? = redis.opsForValue().get(token)

    override fun save(token: String, info: String) {
        redis.opsForValue().set(token, info)
        redis.expire(token, property.expireDay, TimeUnit.DAYS)
    }

    override fun remove(token: String) {
        if (!redis.hasKey(token)) return
        redis.delete(token)
    }

    private val redis: StringRedisTemplate get() = redisTemplate ?: throw RuntimeException()

}