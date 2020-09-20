package com.soiiy.utils.error

class ElementNotFoundError(
        message: String = "ElementNotFoundError!"
): RuntimeError(code = 4002, message = message)