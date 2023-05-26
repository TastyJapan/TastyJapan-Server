package com.tastyjapan.restaurant.service

import com.tastyjapan.city.convertStringToCity
import com.tastyjapan.exception.ErrorType
import com.tastyjapan.exception.ExceptionResponse
import com.tastyjapan.exception.TastyJapanException
import com.tastyjapan.menu.mapper.ConvertStringToMenu
import com.tastyjapan.restaurant.domain.repository.RestaurantRepository
import com.tastyjapan.restaurant.ui.dto.RestaurantDetailResponse
import com.tastyjapan.restaurant.ui.dto.RestaurantResponse
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Stream

// 위도
const val JapanMinLatitude: Double = 20.25
const val JapanMaxLatitude: Double = 45.33

// 경도
const val JapanMinLongitude: Double = 122.93
const val JapanMaxLongitude: Double = 153.98

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
class RestaurantService(
    val restaurantRepository: RestaurantRepository,
    val convertStringToCity: convertStringToCity,
    val convertStringToMenu: ConvertStringToMenu
) {

    fun getAllRestaurants(pageable: Pageable): Slice<RestaurantResponse> {
        return restaurantRepository.findAll(pageable).map { restaurant -> RestaurantResponse(restaurant) }
    }

    fun getRestaurants(
        city: String?,
        menu: String?,
        latMax: Double,
        latMin: Double,
        longMax: Double,
        longMin: Double,
        pageable: Pageable
    ): Slice<RestaurantResponse> {
        checkLatitude(latMax)
        checkLatitude(latMin)
        checkLongitude(longMax)
        checkLongitude(longMin)


        if (city != null && menu != null) {
            val cityEnum = convertStringToCity.convertCityToEnum(city)
            val menuEnum = convertStringToMenu.convertMenuToEnum(menu)
            return restaurantRepository.findRestaurantByCityAndMenu(
                longMin = longMin,
                latMin = latMin,
                longMax = longMax,
                latMax = latMax,
                city = cityEnum,
                menuSort = menuEnum,
                pageable = pageable
            ).map { restaurant -> RestaurantResponse(restaurant) }

        } else if (city != null) {
            val cityEnum = convertStringToCity.convertCityToEnum(city)
            return restaurantRepository.findRestaurantsByCity(
                longMin = longMin,
                latMin = latMin,
                longMax = longMax,
                latMax = latMax,
                city = cityEnum,
                pageable = pageable
            ).map { restaurant -> RestaurantResponse(restaurant) }
        } else if (menu != null) {
            val menuEnum = convertStringToMenu.convertMenuToEnum(menu)
            return restaurantRepository.findRestaurantsByMenu(
                longMin = longMin,
                latMin = latMin,
                longMax = longMax,
                latMax = latMax,
                menuSort = menuEnum,
                pageable = pageable
            ).map { restaurant -> RestaurantResponse(restaurant) }
        } else {
            return restaurantRepository.findRestaurantsByLatAndLong(
                longMin = longMin, latMin = latMin, longMax = longMax, latMax = latMax, pageable = pageable
            ).map { restaurant -> RestaurantResponse(restaurant) }
        }
    }

    fun getRestaurantDetail(restaurantId: Long): RestaurantDetailResponse {
        val restaurant = restaurantRepository.findById(restaurantId).orElseThrow {
            TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.RESTAURANT_NOT_FOUND))
        }

        return Stream.of(restaurant).map { RestaurantDetailResponse(restaurant) }.findFirst()
            .orElseThrow() // or return null or throw exception if necessary

    }

    fun getRestaurantRecommend(): RestaurantResponse {
        val restaurant = restaurantRepository.findRestaurantRecommend()

        return Stream.of(restaurant).map { RestaurantResponse(restaurant) }.findFirst().orElseThrow()
    }

    private fun checkLatitude(latitude: Double) {
        if (latitude < JapanMinLatitude || latitude > JapanMaxLatitude) {
            throw TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.INVALID_LATITUDE))
        }
    }

    private fun checkLongitude(longitude: Double) {
        if (longitude < JapanMinLongitude || longitude > JapanMaxLongitude) {
            throw TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.INVALID_LONGITUDE))
        }
    }

}