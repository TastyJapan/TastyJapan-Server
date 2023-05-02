package com.tastyjapan.restaurant.domain

import com.tastyjapan.menu.domain.Menu
import com.tastyjapan.picture.domain.RestaurantPicture
import com.tastyjapan.review.domain.BlogReview
import com.tastyjapan.review.domain.Review
import com.tastyjapan.group.domain.Group
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RestaurantTest {

    lateinit var restaurant: Restaurant
    @Test
    fun createRestaurant() {
        val id: Long = 1L
        val name: String = "chocochip"
        val longitude: Double = 35.6895
        val latitude: Double = 139.6917
        val address: String = "Tokyo, Japan"
        val rating: Double = 4.5
        val summary: String = "Great Place!"
        val city: City = City.OSAKA;


        restaurant = Restaurant(
            id = id, name = name, longitude=longitude, latitude=latitude, address=address , rating=rating,
            summary = summary, city = city
        )

        assertEquals(id, restaurant.id)
        assertEquals(name, restaurant.name)
        assertEquals(longitude, restaurant.longitude)
        assertEquals(latitude, restaurant.latitude)
        assertEquals(address, restaurant.address)
        assertEquals(rating, restaurant.rating)
    }
}
