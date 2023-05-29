import com.tastyjapan.city.City
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import com.tastyjapan.restaurant.domain.Restaurant
import com.tastyjapan.review.domain.Review
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ReviewTest {
    private lateinit var review: Review
    private lateinit var restaurant: Restaurant
    private lateinit var member: Member

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
        member = Member(id = 1, name = "John Doe", email = "johndoe@example.com", picture = "https://example.com/profile.png", role = Role.USER)
        review = Review(
            id = 1,
            content = "Delicious food and great atmosphere!",
            rating = 4.8
        )
    }

    @Test
    @DisplayName("Review 객체를 생성할 수 있다.")
    fun createReview() {
        assertEquals(1L, review.id)
        assertEquals("Delicious food and great atmosphere!", review.content)
        assertEquals(4.8, review.rating)
        assertEquals(null, review.restaurant)
        assertEquals(null, review.member)
    }

    @Test
    fun testEquals() {
        val otherReview = Review(
            id = review.id,
            content = review.content,
            rating = review.rating
        )
        assertEquals(review, otherReview)
    }

    @Test
    fun testHashCode() {
        val otherReview = Review(
            id = review.id,
            content = review.content,
            rating = review.rating
        )
        assertEquals(review.hashCode(), otherReview.hashCode())
    }

    @Test
    fun testToString() {
        val expectedString =
            "Review(id=${review.id}, content='Delicious food and great atmosphere!', rating=4.8, restaurant=null, member=null)"
        assertEquals(expectedString, review.toString())
    }

    @Test
    fun testRestaurant() {
        review.restaurant = restaurant
        assertEquals(restaurant, review.restaurant)
    }

    @Test
    fun testMember() {
        review.member = member
        assertEquals(member, review.member)
    }
}
