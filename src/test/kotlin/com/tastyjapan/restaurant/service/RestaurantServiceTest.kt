package com.tastyjapan.restaurant.service

import com.tastyjapan.exception.TastyJapanException
import com.tastyjapan.fixture.MemberFixture.createMemberChoco
import com.tastyjapan.fixture.MenuFixture.createFirstRamenMenu
import com.tastyjapan.fixture.MenuFixture.createSushiMenu
import com.tastyjapan.fixture.RestaurantFixture.createRestaurantRamen
import com.tastyjapan.fixture.RestaurantFixture.createRestaurantSushi
import com.tastyjapan.fixture.ReviewFixture.createRamenReview
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.menu.domain.Menu
import com.tastyjapan.menu.domain.repository.MenuRepository
import com.tastyjapan.restaurant.domain.Restaurant
import com.tastyjapan.restaurant.domain.repository.RestaurantRepository
import com.tastyjapan.restaurant.ui.dto.RestaurantResponse
import com.tastyjapan.review.domain.Review
import com.tastyjapan.review.domain.repository.ReviewRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    private lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    private lateinit var menuRepository: MenuRepository

    @Autowired
    private lateinit var restaurantService: RestaurantService


    private lateinit var restaurant1: Restaurant
    private lateinit var restaurant2: Restaurant
    private lateinit var menu1: Menu
    private lateinit var menu2: Menu

    @BeforeEach
    fun setUp() {
        restaurantRepository.deleteAll()
        menuRepository.deleteAll()

        restaurant1 = createRestaurantRamen()
        restaurant2 = createRestaurantSushi()

        menu1 = createFirstRamenMenu()
        menu2 = createSushiMenu()
        restaurant1.addMenu(menu1)
        restaurant2.addMenu(menu2)

        restaurantRepository.saveAll(listOf(restaurant1, restaurant2))

        menuRepository.saveAll(listOf(menu1, menu2))
    }

    @DisplayName("모든 식당 조회에 성공한다.")
    @Test
    fun getAllRestaurants() {
        // Given
        val pageable: Pageable = Pageable.ofSize(10).withPage(0)

        // When
        val result: Slice<RestaurantResponse> = restaurantService.getAllRestaurants(pageable)

        // Then
        assertEquals(2, result.numberOfElements)
    }

    @Test
    fun getRestaurantByCityAndMenu() {
        // Given
        val city = "tokyo"
        val menu = "ramen"
        val latMax = 36.6895
        val latMin = 34.6895
        val longMax = 140.6917
        val longMin = 138.6917
        val pageable: Pageable = PageRequest.of(0, 10)

        // When
        val result: Slice<RestaurantResponse> = restaurantService.getRestaurants(
            city = city,
            menu = menu,
            longMax = longMax,
            longMin = longMin,
            latMax = latMax,
            latMin = latMin,
            pageable = pageable
        )

        // Then
        assertEquals(1, result.numberOfElements)
    }

    @Test
    fun getRestaurantDetail() {
        // Given
        val restaurantId = restaurant1.id

        // When
        val result = restaurantService.getRestaurantDetail(restaurantId)

        // Then
        assertThat(result)
            .extracting("id", "name", "address", "latitude", "longitude", "summary")
            .containsExactly(
                restaurant1.id,
                restaurant1.name,
                restaurant1.address,
                restaurant1.latitude,
                restaurant1.longitude,
                restaurant1.summary
            )
    }


    @Test
    fun checkConvertStringToCity() {
        // Given
        val cityString = "tokyouu"

        // When&Then
        assertThatThrownBy {
            restaurantService.getRestaurants(
                city = cityString,
                menu = "Ramen",
                longMax = restaurant1.longitude + 1,
                longMin = restaurant1.longitude - 1,
                latMax = restaurant1.latitude + 1,
                latMin = restaurant1.latitude - 1,
                pageable = PageRequest.of(0, 10)
            )
        }
            .isInstanceOf(TastyJapanException::class.java)
    }

    @Test
    fun checkConvertStringToMenu() {
        // Given
        val menuString = "ramenan"

        // When&Then
        assertThatThrownBy {
            restaurantService.getRestaurants(
                city = "TOKYO",
                menu = menuString,
                longMax = restaurant1.longitude + 1,
                longMin = restaurant1.longitude - 1,
                latMax = restaurant1.latitude + 1,
                latMin = restaurant1.latitude - 1,
                pageable = PageRequest.of(0, 10)
            )
        }
            .isInstanceOf(TastyJapanException::class.java)
    }
}