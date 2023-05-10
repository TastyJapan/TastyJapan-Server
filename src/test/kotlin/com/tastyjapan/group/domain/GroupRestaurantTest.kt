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
        groupRestaurant = GroupRestaurant()
    }

    @Test
    fun testGetters() {
        assertEquals(mutableSetOf<Group>(), groupRestaurant.groups)
        assertEquals(mutableSetOf<Restaurant>(), groupRestaurant.restaurants)
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
        val member = Member(name = "John Doe", email = "johndoe@example.com", picture = "picture.png", role = Role.USER)
        groupRestaurant.groups.add(group)
        groupRestaurant.restaurants.add(restaurant)

        assertEquals(mutableSetOf<Group>(group), groupRestaurant.groups)
        assertEquals(mutableSetOf<Restaurant>(restaurant), groupRestaurant.restaurants)
    }

}
