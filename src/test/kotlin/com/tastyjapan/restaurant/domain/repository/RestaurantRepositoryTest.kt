package com.tastyjapan.restaurant.domain.repository

import com.tastyjapan.city.City
import com.tastyjapan.fixture.RestaurantFixture.createRestaurantRamen
import com.tastyjapan.fixture.RestaurantFixture.createRestaurantSushi
import com.tastyjapan.restaurant.domain.Restaurant
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class RestaurantRepositoryTest {
    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    lateinit var restaurant1: Restaurant

    @BeforeEach
    fun setUp() {
        restaurantRepository.deleteAll()
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 식당을 저장하면, 해당 ID의 식당은 후에 작성된 식당 정보로 업데이트 된다.")
    @Test
    fun saveSameId() {
        // given
        restaurant1 = createRestaurantRamen()
        restaurantRepository.save(restaurant1)

        // when
        val restaurant2 = Restaurant(
            id = restaurant1.id,
            name = "Tasty Restaurant2",
            longitude = 123.456,
            latitude = 789.012,
            address = "123 Main St",
            summary = "A delicious restaurant",
            rating = 4.5,
            city = City.TOKYO
        )
        restaurantRepository.save(restaurant2)

        // then
        val savedRestaurants = restaurantRepository.findAll()
        assertThat(savedRestaurants.size).isEqualTo(1)

        val foundRestaurant = restaurantRepository.findById(restaurant1.id).get()
        assertThat(foundRestaurant.name).isEqualTo(restaurant2.name)
    }

    @DisplayName("id가 없는 Restaurant 엔티티를 저장하면 순차적으로 ID를 부여하여 저장한다.")
    @Test
    fun saveNoIdRestaurant() {
        // given
        restaurant1 = createRestaurantRamen()
        restaurantRepository.save(restaurant1)

        // when
        val restaurant2 = Restaurant(
            name = "Tasty Restaurant2",
            longitude = 123.456,
            latitude = 789.012,
            address = "123 Main St",
            summary = "A delicious restaurant",
            rating = 4.5,
            city = City.TOKYO
        )
        restaurantRepository.save(restaurant2)

        // then
        val idDiff = restaurant2.id - restaurant1.id
        assertThat(idDiff).isEqualTo(1L);

    }
}