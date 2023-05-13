package com.tastyjapan.global.response

import org.springframework.http.HttpStatus

object ApiUtils {
    fun <T> success(response: T): ApiResponse<T> {
        return ApiResponse(true, response, null)
    }

    fun error(throwable: Throwable, status: HttpStatus): ApiResponse<*> {
        return ApiResponse(false, null, ApiError(throwable, status))
    }

    fun error(message: String, status: HttpStatus): ApiResponse<*> {
        return ApiResponse(false, null, ApiError(message, status))
    }
}
