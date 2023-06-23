package com.tastyjapan.global.response

import org.springframework.http.HttpStatus

class ApiError(
    val message: String,
    val status: Int
) {
    constructor(throwable: Throwable, status: HttpStatus) : this(throwable.message ?: "", status.value())
    constructor(message: String, status: HttpStatus) : this(message, status.value())
}
