package com.tastyjapan.picture.domain

import com.tastyjapan.city.City
import com.tastyjapan.restaurant.domain.Restaurant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RestaurantPictureTest {

    lateinit var restaurantPicture: RestaurantPicture

    @BeforeEach
    fun setUp() {
        restaurantPicture = RestaurantPicture(
            id = 1L,
            url = "https://www.example.com/tasty-japan.jpg",
        )
        val restaurant = Restaurant(
            id = 1L,
            name = "Tasty Japan",
            longitude = 35.6895,
            latitude = 139.6917,
            address = "Tokyo, Japan",
            rating = 4.5,
            summary = "It is sushi",
            city = City.TOKYO
        )
        restaurantPicture.restaurant = restaurant
    }

    @Test
    fun testGetters() {
        assertEquals(1L, restaurantPicture.id)
        assertEquals("https://www.example.com/tasty-japan.jpg", restaurantPicture.url)
     }

}
