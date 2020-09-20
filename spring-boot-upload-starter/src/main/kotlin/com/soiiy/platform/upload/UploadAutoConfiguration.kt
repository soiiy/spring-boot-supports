package com.soiiy.platform.upload

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(UploadProperties::class)
open class UploadAutoConfiguration {

    @Bean
    open fun uploadService(properties: UploadProperties): UploadService =
        UploadService(properties)

}