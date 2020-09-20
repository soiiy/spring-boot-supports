package com.soiiy.security.error

import com.soiiy.utils.error.RuntimeError

// 授权 - 批准
class UnAuthorizationError: RuntimeError(
    code = 403,
    message = "无操作权限！"
)