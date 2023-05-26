package com.tastyjapan.restaurant.domain.repository

import com.tastyjapan.city.City
import com.tastyjapan.menu.domain.MenuSort
import com.tastyjapan.restaurant.domain.Restaurant
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface RestaurantRepositoryCustom {
    fun findRestaurantsByLatAndLong(
        longMin: Double,
        latMin: Double,
        longMax: Double,
        latMax: Double,
        pageable: Pageable
    ): Slice<Restaurant>

    fun findRestaurantByCityAndMenu(
        longMin: Double,
        latMin: Double,
        longMax: Double,
        latMax: Double,
        city: City, menuSort: MenuSort, pageable: Pageable
    ): Slice<Restaurant>

    fun findRestaurantsByMenu(
        longMin: Double,
        latMin: Double,
        longMax: Double,
        latMax: Double, menuSort: MenuSort, pageable: Pageable
    ): Slice<Restaurant>

    fun findRestaurantsByCity(
        longMin: Double,
        latMin: Double,
        longMax: Double,
        latMax: Double, city: City, pageable: Pageable
    ): Slice<Restaurant>

    fun findRestaurantRecommend(): Restaurant
    fun checkRestaurantId(restaurantId: Long): Boolean
}