package com.tastyjapan.group.domain

import com.tastyjapan.city.City
import com.tastyjapan.restaurant.domain.Restaurant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GroupsRestaurantTest {

    lateinit var groupRestaurant: GroupRestaurant

    @Test
    fun testGroupRestaurant() {
        // Create a GroupRestaurant instance
        val groups = Groups(title = "Foodies")
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

        val groupRestaurant = GroupRestaurant(
            groups = groups,
            restaurants = restaurant
        )

        // Assert the values
        assertEquals(groups, groupRestaurant.groups)
        assertEquals(restaurant, groupRestaurant.restaurants)
    }

}
