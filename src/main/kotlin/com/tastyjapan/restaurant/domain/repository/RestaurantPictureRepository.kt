package com.tastyjapan.restaurant.domain.repository


import com.tastyjapan.restaurant.domain.RestaurantPicture
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantPictureRepository: JpaRepository<RestaurantPicture, Long>, RestaurantPictureRepositoryCustom{
}