package com.tastyjapan.exception

import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestController
@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(TastyJapanException::class)
    fun handleLocoException(locoException: TastyJapanException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity.status(locoException.httpStatus)
            .body(locoException.body)
    }
}