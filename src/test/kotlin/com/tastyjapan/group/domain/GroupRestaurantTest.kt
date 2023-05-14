package com.tastyjapan.group.domain

import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import com.tastyjapan.restaurant.domain.City
import com.tastyjapan.restaurant.domain.Restaurant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GroupRestaurantTest {

    lateinit var groupRestaurant: GroupRestaurant

    @BeforeEach
    fun setUp() {
        groupRestaurant = GroupRestaurant(
            id = 1L
        )
    }

    @Test
    fun testGetters() {
        assertEquals(1L, groupRestaurant.id)
        assertEquals(mutableSetOf<Group>(), groupRestaurant.groups)
        assertEquals(mutableSetOf<Restaurant>(), groupRestaurant.restaurants)
    }

    @Test
    fun testSetters() {
        val group = Group(
            id = 1L,
            title = "Foodies",
            member = Member(1L, "John Doe", "johndoe@example.com", "picture.png", Role.USER)
        )
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

        groupRestaurant.groups.add(group)
        groupRestaurant.restaurants.add(restaurant)

        assertEquals(mutableSetOf<Group>(group), groupRestaurant.groups)
        assertEquals(mutableSetOf<Restaurant>(restaurant), groupRestaurant.restaurants)
    }

}
