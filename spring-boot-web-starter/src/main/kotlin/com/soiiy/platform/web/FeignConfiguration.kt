//package com.soiiy.platform.web
//
//import com.soiiy.utils.http.ResponseError
//import com.soiiy.platform.utils.json.JsonUtil
//import feign.Feign
//import feign.Response
//import feign.codec.ErrorDecoder
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
//import org.springframework.context.annotation.Configuration
//import java.nio.charset.StandardCharsets
//
//@Configuration
//@ConditionalOnClass(Feign::class)
//open class FeignConfiguration: ErrorDecoder {
//
//    override fun decode(methodKey: String, response: Response): Exception {
//        return try {
//            val responseTxt = response.body().asReader(StandardCharsets.UTF_8).readText()
//            val result = JsonUtil.parseObject(responseTxt, ResponseError::class.java)
//            FeignRequestExcption(result)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            FeignRequestExcption(ResponseError(errMsg = "MiddleWork Error!"))
//        }
//    }
//
//}