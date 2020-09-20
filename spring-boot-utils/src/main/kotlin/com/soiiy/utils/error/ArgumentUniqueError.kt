package com.soiiy.utils.error

class ArgumentUniqueError(
        message: String = "ArgumentUniqueError!"
): RuntimeError(code = 1002, message = message)