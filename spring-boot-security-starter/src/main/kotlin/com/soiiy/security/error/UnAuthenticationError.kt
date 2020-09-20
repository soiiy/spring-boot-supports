package com.soiiy.security.error

import com.soiiy.utils.error.RuntimeError

class UnAuthenticationError(message: String = "需要登陆！"): RuntimeError(
    code = 401,
    message = message
)