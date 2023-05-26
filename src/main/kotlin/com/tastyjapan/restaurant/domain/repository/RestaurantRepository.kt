package com.tastyjapan.restaurant.domain.repository

import com.tastyjapan.restaurant.domain.Restaurant
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantRepository: JpaRepository<Restaurant, Long>, RestaurantRepositoryCustom{
}



