package com.soiiy.platform.security.error

import com.soiiy.platform.utils.error.RuntimeError

class UnAuthenticationError(message: String = "需要登陆！"): RuntimeError(
    code = 401,
    message = message
)