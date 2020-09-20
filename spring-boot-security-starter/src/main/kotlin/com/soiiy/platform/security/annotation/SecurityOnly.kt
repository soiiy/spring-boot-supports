package com.soiiy.platform.security.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class SecurityOnly(
    val roles: Array<String> = [],
    val permissions: Array<String> = []
)