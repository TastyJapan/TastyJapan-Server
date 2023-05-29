package com.tastyjapan.exception

import lombok.Getter

@Getter
class ExceptionResponse (var errorCode: String, var message: String){
    constructor(errorType: ErrorType) : this(errorType.errorCode, errorType.message)
}