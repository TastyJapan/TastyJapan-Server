package com.tastyjapan.restaurant.domain.repository

import com.tastyjapan.city.City
import com.tastyjapan.menu.domain.MenuSort
import com.tastyjapan.restaurant.domain.Restaurant
import com.tastyjapan.restaurant.domain.RestaurantPicture
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface RestaurantPictureRepositoryCustom {
   fun findPictureByRestaurantId(restaurantId: Long): List<RestaurantPicture>
}