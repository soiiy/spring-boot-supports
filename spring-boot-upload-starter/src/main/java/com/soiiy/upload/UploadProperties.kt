package com.soiiy.upload

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.upload")
class UploadProperties {

    var cdn: String = ""

    var bucket: String = ""

    var endpoint: String = ""

    var access: String = ""

    var secret: String = ""

}