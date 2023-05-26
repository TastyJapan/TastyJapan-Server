package com.tastyjapan.restaurant.domain

import com.tastyjapan.city.City
import com.tastyjapan.group.domain.GroupRestaurant
import com.tastyjapan.group.domain.Groups
import com.tastyjapan.menu.domain.Menu
import com.tastyjapan.menu.domain.MenuSort
import com.tastyjapan.review.domain.BlogReview
import com.tastyjapan.review.domain.BlogSource
import com.tastyjapan.review.domain.ExternalReview
import com.tastyjapan.review.domain.Review
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class RestaurantTest {
    private lateinit var restaurant: Restaurant

    @BeforeEach
    fun setup() {
        restaurant = Restaurant(
            name = "Tasty Restaurant",
            longitude = 123.456,
            latitude = 789.012,
            address = "123 Main St",
            summary = "A delicious restaurant",
            rating = 4.5,
            city = City.TOKYO
        )
    }

    @Test
    @DisplayName("Restaurant 객체를 생성할 수 있다.")
    fun createRestaurant() {
        assertEquals("Tasty Restaurant", restaurant.name)
        assertEquals(123.456, restaurant.longitude)
        assertEquals(789.012, restaurant.latitude)
        assertEquals("123 Main St", restaurant.address)
        assertEquals("A delicious restaurant", restaurant.summary)
        assertEquals(4.5, restaurant.rating)
        assertEquals(City.TOKYO, restaurant.city)
    }

    @Test
    fun testEquals() {
        val otherRestaurant = Restaurant(
            name = restaurant.name,
            longitude = restaurant.longitude,
            latitude = restaurant.latitude,
            address = restaurant.address,
            summary = restaurant.summary,
            rating = restaurant.rating,
            city = restaurant.city
        )
        assertEquals(restaurant, otherRestaurant)
    }

    @Test
    fun testHashCode() {
        val otherRestaurant = Restaurant(
            id = restaurant.id,
            name = restaurant.name,
            longitude = restaurant.longitude,
            latitude = restaurant.latitude,
            address = restaurant.address,
            summary = restaurant.summary,
            rating = restaurant.rating,
            city = restaurant.city
        )
        assertEquals(restaurant.hashCode(), otherRestaurant.hashCode())
    }

    @Test
    fun testToString() {
        val expectedString =
            "Restaurant(id=${restaurant.id}, name='Tasty Restaurant', longitude=123.456, latitude=789.012, address='123 Main St', summary='A delicious restaurant', rating=4.5, city=TOKYO)"
        assertEquals(expectedString, restaurant.toString())
    }

    @Test
    fun testRestaurantPictures() {
        val picture = RestaurantPicture(id = 1, url = "https://example.com/restaurant.jpg")
        restaurant.restaurantPictures.add(picture)
        assertEquals(listOf(picture), restaurant.restaurantPictures)
    }

    @Test
    fun testBlogReviews() {
        val review = BlogReview(id = 1, url = "https://example.com/review", source = BlogSource.NAVER)
        restaurant.blogReviews.add(review)
        assertEquals(listOf(review), restaurant.blogReviews)
    }

    @Test
    fun testExternalReviews() {
        val review = ExternalReview(
            id = 1,
            content = "Great food!",
            url = "https://example.com/review",
            nickname = "FoodLover",
            rating = 4.8,
            source = "External"
        )
        restaurant.externalReviews.add(review)
        assertEquals(listOf(review), restaurant.externalReviews)
    }

    @Test
    fun testReviews() {
        val review = Review(id = 1, content = "Delicious dishes!", rating = 4.2)
        restaurant.reviews.add(review)
        assertEquals(listOf(review), restaurant.reviews)
    }

    @Test
    fun testMenus() {
        val menu = Menu(
            id = 1,
            name = "Spaghetti",
            price = 15L,
            menu_sort = MenuSort.RAMEN
        )
        restaurant.menus.add(menu)
        assertEquals(listOf(menu), restaurant.menus)
    }

    @Test
    fun testGroupRestaurantList() {
        val groupRestaurant = GroupRestaurant(
            id = 1,
            groups = Groups(id = 1, title = "Foodies"),
            restaurants = restaurant
        )
        restaurant.groupRestaurantList.add(groupRestaurant)
        assertEquals(listOf(groupRestaurant), restaurant.groupRestaurantList)
    }
}