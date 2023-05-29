package com.tastyjapan.group.domain

import com.tastyjapan.city.City
import com.tastyjapan.restaurant.domain.Restaurant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GroupsRestaurantTest {

    private lateinit var groupRestaurant: GroupRestaurant
    private lateinit var groups: Groups
    private lateinit var restaurant: Restaurant

    @BeforeEach
    fun setup() {
        // Initialize the necessary objects
        groups = Groups(title = "Foodies")
        restaurant = Restaurant(
            id = 1L,
            name = "Tasty Japan",
            longitude = 35.6895,
            latitude = 139.6917,
            address = "Tokyo, Japan",
            rating = 4.5,
            city = City.TOKYO,
            summary = "Great Place"
        )
        groupRestaurant = GroupRestaurant(groups = groups, restaurants = restaurant)
    }

    @Test
    fun testGroupRestaurant() {

        val groupRestaurant = GroupRestaurant(
            groups = groups,
            restaurants = restaurant
        )

        assertEquals(groups, groupRestaurant.groups)
        assertEquals(restaurant, groupRestaurant.restaurants)
    }

    @Test
    fun testEquals() {
        val otherGroupRestaurant = GroupRestaurant(groups = groups, restaurants = restaurant)

        assertEquals(groupRestaurant, otherGroupRestaurant)
    }

    @Test
    fun testHashCode() {
        val otherGroupRestaurant = GroupRestaurant(groups = groups, restaurants = restaurant)

        assertEquals(groupRestaurant.hashCode(), otherGroupRestaurant.hashCode())
    }

    @Test
    fun testToString() {
        // Test the toString method
        val expected = "GroupRestaurant(id=${groupRestaurant.id}, groups=${groups.id}, restaurants=${restaurant.id})"
        assertEquals(expected, groupRestaurant.toString())
    }
}
