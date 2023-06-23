package com.tastyjapan.exception

import com.tastyjapan.global.response.ApiUtils
import lombok.Getter
import org.springframework.http.HttpStatus

@Getter
class ExceptionResponse(var errorCode: String, var message: String) {

    constructor(errorType: ErrorType, status: HttpStatus) : this(errorType.errorCode, errorType.message) {
        ApiUtils.error(this.message, status)
    }

    constructor(errorType: ErrorType) : this(errorType.errorCode, errorType.message) {
        ApiUtils.error(this.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }


}