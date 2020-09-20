package com.soiiy.platform.utils.error

class ElementNotAvailableError(
        message: String = "ElementNotAvailableError!"
): RuntimeError(code = 4001, message = message)