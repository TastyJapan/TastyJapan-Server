package com.tastyjapan.review.domain

import com.tastyjapan.restaurant.domain.City
import com.tastyjapan.restaurant.domain.Restaurant
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BlogReviewTest {
    lateinit var blogReview: BlogReview

    @Test
    fun createBlogReviewTest() {
        blogReview = BlogReview(
            id = 1L,
            url = "https://www.yelp.com/biz/tasty-japan-tokyo",
            source = "Tablelog"
        )
        val restaurant = Restaurant(
            id = 1L,
            name = "Tasty Japan",
            longitude = 35.6895,
            latitude = 139.6917,
            address = "Tokyo, Japan",
            rating = 4.5,
            summary = "Great Place",
            city = City.TOKYO
        )
        blogReview.restaurant = restaurant

        assertEquals(1L, blogReview.id)
        assertEquals("https://www.yelp.com/biz/tasty-japan-tokyo", blogReview.url)
        assertEquals("Tablelog", blogReview.source)
        assertEquals(
            Restaurant(1L, "Tasty Japan", 35.6895, 139.6917, "Tokyo, Japan", "Great Place", 4.5, City.TOKYO),
            blogReview.restaurant
        )
    }
}