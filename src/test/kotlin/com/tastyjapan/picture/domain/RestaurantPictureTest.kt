import com.tastyjapan.city.City
import com.tastyjapan.restaurant.domain.RestaurantPicture
import com.tastyjapan.restaurant.domain.Restaurant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class RestaurantPictureTest {
    private lateinit var restaurantPicture: RestaurantPicture
    private lateinit var restaurant: Restaurant

    @BeforeEach
    fun setup() {
        val id: Long = 1L
        val name: String = "chocochip"
        val longitude: Double = 35.6895
        val latitude: Double = 139.6917
        val address: String = "Tokyo, Japan"
        val rating: Double = 4.5
        val summary: String = "Great Place!"
        val city: City = City.OSAKA;


        restaurant = Restaurant(
            id = id, name = name, longitude=longitude, latitude=latitude, address=address , rating=rating,
            summary = summary, city = city
        )
        restaurantPicture = RestaurantPicture(id = 1, url = "https://example.com/image.jpg")
    }

    @Test
    @DisplayName("RestaurantPicture 객체를 생성할 수 있다.")
    fun createRestaurantPicture() {
        assertEquals(1L, restaurantPicture.id)
        assertEquals("https://example.com/image.jpg", restaurantPicture.url)
        assertEquals(null, restaurantPicture.restaurant)
    }

    @Test
    fun testEquals() {
        val otherRestaurantPicture = RestaurantPicture(
            id = restaurantPicture.id,
            url = restaurantPicture.url
        )
        assertEquals(restaurantPicture, otherRestaurantPicture)
    }

    @Test
    fun testHashCode() {
        val otherRestaurantPicture = RestaurantPicture(
            id = restaurantPicture.id,
            url = restaurantPicture.url
        )
        assertEquals(restaurantPicture.hashCode(), otherRestaurantPicture.hashCode())
    }

    @Test
    fun testToString() {
        val expectedString =
            "RestaurantPicture(id=${restaurantPicture.id}, url='https://example.com/image.jpg', restaurant=null)"
        assertEquals(expectedString, restaurantPicture.toString())
    }

    @Test
    fun testRestaurant() {
        restaurantPicture.restaurant = restaurant
        assertEquals(restaurant, restaurantPicture.restaurant)
    }
}
