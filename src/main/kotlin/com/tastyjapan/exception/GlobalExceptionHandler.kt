package com.tastyjapan.exception

import lombok.extern.slf4j.Slf4j
import org.apache.logging.log4j.LogManager
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {
    val log = LogManager.getLogger()
    @ExceptionHandler(TastyJapanException::class)
    fun handleLocoException(tastyJapanException: TastyJapanException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity.status(tastyJapanException.httpStatus)
            .body(tastyJapanException.body)
    }


    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ExceptionResponse> {
        val unexpectedErrorTrace = ExceptionUtils.getStackTrace(e)
        log.error(unexpectedErrorTrace)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ExceptionResponse(ErrorType.UNEXPECTED_SERVER_ERROR))
    }
}