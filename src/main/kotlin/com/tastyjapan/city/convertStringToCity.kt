package com.tastyjapan.city

import com.tastyjapan.exception.ErrorType
import com.tastyjapan.exception.ExceptionResponse
import com.tastyjapan.exception.TastyJapanException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class convertStringToCity {
    fun convertCityToEnum(city: String): City {
        try {
            return City.valueOf(city.uppercase())
        } catch (e: IllegalArgumentException) {
            throw TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.CITY_NOT_FOUND))
        }

    }
}