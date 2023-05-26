package com.tastyjapan.city

import org.springframework.stereotype.Component

@Component
class convertStringToCity {
    fun convertCityToEnum(city: String): City {
        return City.valueOf(city.uppercase())
    }
}