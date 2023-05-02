package com.tastyjapan.group.domain

import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import com.tastyjapan.restaurant.domain.City
import com.tastyjapan.restaurant.domain.Restaurant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GroupTest {

    lateinit var save: Group

    @BeforeEach
    fun setUp() {
        save = Group(
            id = 1L,
            title = "My Favorite Restaurants",
            member = Member(1L, "John Doe", "johndoe@example.com", "picture.png", Role.USER)
        )
    }

    @Test
    fun createSaveTest() {
        assertEquals(1L, save.id)
        assertEquals("My Favorite Restaurants", save.title)
        assertEquals(Member(1L, "John Doe", "johndoe@example.com", "picture.png", Role.USER), save.member)
    }

}
