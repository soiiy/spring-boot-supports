package com.soiiy.platform.utils.error

class FormValidationError(
    errors: List<Pair<String, String>>
): RuntimeError(
    code = 2001,
    message = errors.first().second,
    detail = errors.toMap()
)