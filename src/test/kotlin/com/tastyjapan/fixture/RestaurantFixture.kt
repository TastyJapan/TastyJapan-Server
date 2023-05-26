package com.tastyjapan.fixture

import com.tastyjapan.city.City
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import com.tastyjapan.restaurant.domain.Restaurant

object RestaurantFixture {
    fun createRestaurantRamen(): Restaurant {
        return Restaurant(
            name = "Tasty Japan",
            longitude = 139.6917,
            latitude = 35.6895,
            address = "Tokyo, Japan",
            rating = 4.5,
            city = City.TOKYO,
            summary = "Great Place"
        )
    }

    fun createRestaurantSushi(): Restaurant {
        return Restaurant(
            name = "하루코마 본점",
            longitude = 135.5134,
            latitude = 35.6895,
            address = "5 Chome-5-2 Tenjinbashi, Kita Ward, Osaka, 530-0041 일본",
            rating = 4.2,
            city = City.OSAKA,
            summary = "스시/초밥집"
        )
    }
}