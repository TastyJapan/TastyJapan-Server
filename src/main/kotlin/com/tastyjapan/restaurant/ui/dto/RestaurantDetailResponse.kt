package com.tastyjapan.restaurant.ui.dto

import com.tastyjapan.city.City
import com.tastyjapan.restaurant.domain.Restaurant

data class RestaurantDetailResponse(
    val id: Long,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val address: String,
    val summary: String,
    val rating: Double,
    val city: City,
    val reviewCount: Int = 0,
    val blogReviewCount: Int = 0,
    val restaurantImages: MutableList<String> = mutableListOf(),
) {
    constructor(restaurant: Restaurant) : this(
        id = restaurant.id,
        name = restaurant.name,
        longitude = restaurant.longitude,
        latitude = restaurant.latitude,
        address = restaurant.address,
        summary = restaurant.summary,
        city = restaurant.city,
        rating = restaurant.rating,
        reviewCount = restaurant.reviews.size,
        blogReviewCount = restaurant.blogReviews.size,
        restaurantImages = restaurant.restaurantPictures.map { it.toString() }.toMutableList()
    )
}