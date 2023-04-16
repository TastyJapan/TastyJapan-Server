package com.tastyjapan.exception

import org.springframework.http.HttpStatus

class TastyJapanException(val httpStatus: HttpStatus, val body:ExceptionResponse):RuntimeException() {
}