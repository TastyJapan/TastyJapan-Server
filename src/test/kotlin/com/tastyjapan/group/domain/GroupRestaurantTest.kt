package com.tastyjapan.group.domain

import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import com.tastyjapan.city.City
import com.tastyjapan.restaurant.domain.Restaurant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GroupRestaurantTest {

    lateinit var groupRestaurant: GroupRestaurant

    @BeforeEach
    fun setUp() {
        groupRestaurant = GroupRestaurant()
    }

    @Test
    fun testSetters() {
        val group = Group(title = "Foodies")
        val restaurant = Restaurant(
            id = 1L,
            name = "Tasty Japan",
            longitude = 35.6895,
            latitude = 139.6917,
            address = "Tokyo, Japan",
            rating = 4.5,
            city = City.TOKYO,
            summary = "Great Place"
        )
        groupRestaurant.groups = group
        groupRestaurant.restaurants = restaurant

        assertEquals(group, groupRestaurant.groups)
        assertEquals(restaurant, groupRestaurant.restaurants)
    }

}
