package com.tastyjapan.review.domain

import com.tastyjapan.restaurant.domain.City
import com.tastyjapan.restaurant.domain.Restaurant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExternalReviewTest {

    lateinit var externalReview: ExternalReview

    @BeforeEach
    fun setUp() {
        externalReview = ExternalReview(
            id = 1L,
            content = "This is a great restaurant!",
            url = "https://www.example.com/user123.jpg",
            nickname = "User123",
            rating = 4.5,
            restaurant = Restaurant(
                id = 1L,
                name = "Tasty Japan",
                longitude = 35.6895,
                latitude = 139.6917,
                address = "Tokyo, Japan",
                rating = 4.5,
                summary = "It is tasty",
                city = City.TOKYO
            )
        )
    }

    @Test
    fun testGetters() {
        assertEquals(1L, externalReview.id)
        assertEquals("This is a great restaurant!", externalReview.content)
        assertEquals("https://www.example.com/user123.jpg", externalReview.url)
        assertEquals("User123", externalReview.nickname)
        assertEquals(4.5, externalReview.rating)
    }

}
