package com.tastyjapan.security.exception

import com.tastyjapan.exception.ErrorType

class BusinessException(
    val errorType: ErrorType
) : RuntimeException()