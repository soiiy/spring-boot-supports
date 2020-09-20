package com.soiiy.platform.utils.error

class ArgumentInvalidError(
        message: String = "ArgumentInvalidError!"
): RuntimeError(code = 1001, message = message)