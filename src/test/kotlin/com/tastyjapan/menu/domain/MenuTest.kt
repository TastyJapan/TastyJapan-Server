package com.tastyjapan.menu.domain

import com.tastyjapan.city.City
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

    @Test
    fun testEquals() {
        val otherMenu = Menu(
            id = menu.id,
            name = menu.name,
            price = menu.price,
            menu_sort = menu.menu_sort,
            pictures = menu.pictures.toMutableList()
        )
        assertEquals(menu, otherMenu)
    }

    @Test
    fun testHashCode() {
        val otherMenu = Menu(
            id = menu.id,
            name = menu.name,
            price = menu.price,
            menu_sort = menu.menu_sort,
            pictures = menu.pictures.toMutableList()
        )
        assertEquals(menu.hashCode(), otherMenu.hashCode())
    }

    @Test
    fun testToString() {
        val expectedString =
            "Menu(id=${menu.id}, name='${menu.name}', price=${menu.price}, menu_sort=${menu.menu_sort}, pictures=${menu.pictures}, restaurant=${menu.restaurant?.id})"
        assertEquals(expectedString, menu.toString())
    }
}
