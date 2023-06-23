package com.tastyjapan.global.response

class ApiResponse<T> (
    private val success: Boolean,
    private val response: T?,
    private val error: ApiError?
) {
    fun isSuccess(): Boolean = success

    fun getResponse(): T? = response
}