import com.tastyjapan.city.City
import com.tastyjapan.restaurant.domain.Restaurant
import com.tastyjapan.review.domain.ExternalReview
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ExternalReviewTest {
    private lateinit var externalReview: ExternalReview
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
        externalReview = ExternalReview(
            id = 1,
            content = "Great food and excellent service!",
            url = "https://example.com/review",
            nickname = "FoodLover123",
            rating = 4.5,
            source = "Review Website"
        )
    }

    @Test
    @DisplayName("ExternalReview 객체를 생성할 수 있다.")
    fun createExternalReview() {
        assertEquals(1L, externalReview.id)
        assertEquals("Great food and excellent service!", externalReview.content)
        assertEquals("https://example.com/review", externalReview.url)
        assertEquals("FoodLover123", externalReview.nickname)
        assertEquals(4.5, externalReview.rating)
        assertEquals("Review Website", externalReview.source)
        assertEquals(null, externalReview.restaurant)
    }

    @Test
    fun testEquals() {
        val otherExternalReview = ExternalReview(
            id = externalReview.id,
            content = externalReview.content,
            url = externalReview.url,
            nickname = externalReview.nickname,
            rating = externalReview.rating,
            source = externalReview.source
        )
        assertEquals(externalReview, otherExternalReview)
    }

    @Test
    fun testHashCode() {
        val otherExternalReview = ExternalReview(
            id = externalReview.id,
            content = externalReview.content,
            url = externalReview.url,
            nickname = externalReview.nickname,
            rating = externalReview.rating,
            source = externalReview.source
        )
        assertEquals(externalReview.hashCode(), otherExternalReview.hashCode())
    }

    @Test
    fun testToString() {
        val expectedString =
            "ExternalReview(id=${externalReview.id}, content='Great food and excellent service!', url='https://example.com/review', nickname='FoodLover123', rating=4.5, source='Review Website', restaurant=null)"
        assertEquals(expectedString, externalReview.toString())
    }

    @Test
    fun testRestaurant() {
        externalReview.restaurant = restaurant
        assertEquals(restaurant, externalReview.restaurant)
    }
}
