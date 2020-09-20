package com.soiiy.upload

import com.qcloud.cos.COSClient
import com.qcloud.cos.ClientConfig
import com.qcloud.cos.auth.BasicCOSCredentials
import com.qcloud.cos.region.Region
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class UploadService(private val properties: UploadProperties) {

    private val region = Region(properties.endpoint)

    private val config = ClientConfig(region)

    private val cred = BasicCOSCredentials(properties.access, properties.secret)

    private val client = COSClient(cred, config)


    fun store(prefix: String = "", file: InputStream): String {
        val dfInfo = DateTimeFormatter.ofPattern("yyyyMMdd/HHmm-").format(LocalDateTime.now())
        val prefixUrl = if (prefix.endsWith("/")) prefix else "$prefix/"
        val path = prefixUrl + dfInfo + randomNumber()

        client.putObject(properties.bucket, path, file, null)

        val cdnUrl = if (properties.cdn.endsWith("/")) properties.cdn else "${properties.cdn}/"
        return cdnUrl + path
    }

    fun copy(prefix: String = "", from: String): String {
        val dfInfo = DateTimeFormatter.ofPattern("yyyyMMdd/HHmm-").format(LocalDateTime.now())
        val prefixUrl = if (prefix.endsWith("/")) prefix else "$prefix/"
        val path = prefixUrl + dfInfo + randomNumber()

        val connection = URL(from).openConnection() as HttpURLConnection
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val stream = connection.inputStream
            client.putObject(properties.bucket, path, stream, null)
        }
        val cdnUrl = if (properties.cdn.endsWith("/")) properties.cdn else "${properties.cdn}/"
        return cdnUrl + path
    }

    private fun randomNumber(): Int {
        val n = Random().nextInt(999999)
        if (n > 100000) return n
        return randomNumber()
    }

}