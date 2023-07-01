package com.tastyjapan.exception

import com.tastyjapan.global.response.ApiResponse
import com.tastyjapan.security.exception.BusinessException
import lombok.extern.slf4j.Slf4j
import org.apache.logging.log4j.LogManager
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolationException


@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {
    val log = LogManager.getLogger()

    @ExceptionHandler(TastyJapanException::class)
    fun handleTastyJapanException(tastyJapanException: TastyJapanException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity.status(tastyJapanException.httpStatus)
            .body(tastyJapanException.body)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBaseException(e: BusinessException): ResponseEntity<ExceptionResponse> {
        log.error(e.errorType.message)
        return ResponseEntity.badRequest()
            .body(ExceptionResponse(ErrorType.OAUTH_ERROR, HttpStatus.BAD_REQUEST))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ExceptionResponse> {
        val result = e.bindingResult.fieldErrors[0]
        val message = "[${result.field}](은)는 ${result.defaultMessage}. 입력된 값: [${result.rejectedValue}]"
        log.error(message)
        return ResponseEntity.badRequest()
            .body(ExceptionResponse(ErrorType.MISSING_REQUIRED_VALUE_ERROR, HttpStatus.BAD_REQUEST))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException): ResponseEntity<ExceptionResponse> {
        val message = ex.constraintViolations.joinToString(", ") { "${it.propertyPath.last()}(은)는 ${it.message}" }
        log.error(message)
        return ResponseEntity.badRequest()
            .body(ExceptionResponse(ErrorType.INVALID_REQUEST_ERROR, HttpStatus.BAD_REQUEST))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ExceptionResponse> {
        val unexpectedErrorTrace = ExceptionUtils.getStackTrace(e)
        log.error(unexpectedErrorTrace)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ExceptionResponse(ErrorType.UNEXPECTED_SERVER_ERROR))
    }
}