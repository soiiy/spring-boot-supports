package com.soiiy.utils.http

import com.soiiy.utils.error.FormValidationError
import javax.validation.Validation

object RequestUtil {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    fun validate(source: Any) {
        val results = validator.validate(source)
        if (results.size == 0) return
        val errors = results.map { Pair(it.propertyPath.toString(), it.message) }
        throw FormValidationError(errors)
    }

}