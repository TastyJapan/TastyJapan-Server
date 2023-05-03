package com.tastyjapan.review.domain

import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import com.tastyjapan.restaurant.domain.City
import com.tastyjapan.restaurant.domain.Restaurant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReviewTest {

    lateinit var review: Review

    @BeforeEach
    fun setUp() {
        review = Review(
            id = 1L,
            content = "This is a great restaurant!",
            restaurant = Restaurant(
                id = 1L,
                name = "Tasty Japan",
                longitude = 35.6895,
                latitude = 139.6917,
                address = "Tokyo, Japan",
                rating = 4.5,
                summary = "It is tasty",
                city = City.TOKYO
            ),
            member = Member(1L, "John Doe", "johndoe@example.com", "picture.png", Role.USER)
        )
    }

    @Test
    fun testGetters() {
        assertEquals(1L, review.id)
        assertEquals("This is a great restaurant!", review.content)
    }
}
