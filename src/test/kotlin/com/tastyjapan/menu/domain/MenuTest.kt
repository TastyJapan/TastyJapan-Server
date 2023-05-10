package com.tastyjapan.menu.domain

import com.tastyjapan.restaurant.domain.City
import com.tastyjapan.restaurant.domain.Restaurant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MenuTest {

    lateinit var menu: Menu

    @BeforeEach
    fun setUp() {
        menu = Menu(
            id = 1L,
            name = "Tonkotsu Ramen",
            price = 1000L,
            menu_sort = MenuSort.RAMEN,
            pictures = mutableListOf("https://www.example.com/ramen.jpg"),
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
        menu.restaurant = restaurant
    }

    @Test
    fun testGetters() {
        assertEquals(1L, menu.id)
        assertEquals("Tonkotsu Ramen", menu.name)
        assertEquals(1000L, menu.price)
        assertEquals(MenuSort.RAMEN, menu.menu_sort)
        assertEquals(mutableListOf("https://www.example.com/ramen.jpg"), menu.pictures)
    }

}
