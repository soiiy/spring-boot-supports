package com.soiiy.utils.error

class ElementNotAvailableError(
        message: String = "ElementNotAvailableError!"
): RuntimeError(code = 4001, message = message)