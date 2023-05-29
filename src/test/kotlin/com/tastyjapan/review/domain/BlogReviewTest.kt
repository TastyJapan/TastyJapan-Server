import com.tastyjapan.city.City
import com.tastyjapan.restaurant.domain.Restaurant
import com.tastyjapan.review.domain.BlogReview
import com.tastyjapan.review.domain.BlogSource
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class BlogReviewTest {
    private lateinit var blogReview: BlogReview
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
        blogReview = BlogReview(id = 1, url = "https://example.com/review", source = BlogSource.NAVER)
    }

    @Test
    @DisplayName("BlogReview 객체를 생성할 수 있다.")
    fun createBlogReview() {
        assertEquals(1L, blogReview.id)
        assertEquals("https://example.com/review", blogReview.url)
        assertEquals(BlogSource.NAVER, blogReview.source)
        assertEquals(null, blogReview.restaurant)
    }

    @Test
    fun testEquals() {
        val otherBlogReview = BlogReview(
            id = blogReview.id,
            url = blogReview.url,
            source = blogReview.source
        )
        assertEquals(blogReview, otherBlogReview)
    }

    @Test
    fun testHashCode() {
        val otherBlogReview = BlogReview(
            id = blogReview.id,
            url = blogReview.url,
            source = blogReview.source
        )
        assertEquals(blogReview.hashCode(), otherBlogReview.hashCode())
    }

    @Test
    fun testToString() {
        val expectedString =
            "BlogReview(id=${blogReview.id}, url='https://example.com/review', source='Food Blog', restaurant=null)"
        assertEquals(expectedString, blogReview.toString())
    }

    @Test
    fun testRestaurant() {
        blogReview.restaurant = restaurant
        assertEquals(restaurant, blogReview.restaurant)
    }
}
