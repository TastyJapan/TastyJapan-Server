package com.tastyjapan.group.ui.dto

import com.tastyjapan.restaurant.ui.dto.RestaurantResponse

data class GroupWithRestaurantResponse(
    val title: String,
    val restaurantList: List<RestaurantResponse>
){
}