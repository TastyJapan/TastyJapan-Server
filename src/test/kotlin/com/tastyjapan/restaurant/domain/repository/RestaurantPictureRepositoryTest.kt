package com.tastyjapan.restaurant.domain.repository

import com.tastyjapan.restaurant.domain.RestaurantPicture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class RestaurantPictureRepositoryTest {

    @Autowired
    lateinit var restaurantPictureRepository: RestaurantPictureRepository

    lateinit var restaurantPicture: RestaurantPicture

    @BeforeEach
    fun setUp() {
        restaurantPictureRepository.deleteAll()
        restaurantPicture = RestaurantPicture(url = "https://www.google.com")
        restaurantPictureRepository.save(restaurantPicture)
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 메뉴를 저장하면, 해당 ID의 메뉴는 후에 작성된 메뉴 정보로 업데이트 된다.")
    @Test
    fun saveSameId() {
        //given
        val restaurantPicture2 = RestaurantPicture(
            id = restaurantPicture.id,
            url = "https://www.naver.com"
        )

        //when
        restaurantPictureRepository.save(restaurantPicture2)

        //then
        assertThat(
            restaurantPictureRepository.findById(restaurantPicture.id).get().url
        ).isEqualTo(restaurantPicture2.url)
    }

    @DisplayName("id가 없는 Menu 엔티티를 저장하면 순차적으로 ID를 부여하여 저장한다.")
    @Test
    fun saveNoIdGroup() {
        //given
        val restaurantPicture2 = RestaurantPicture(
            url = "https://www.naver.com"
        )
        restaurantPictureRepository.save(restaurantPicture2)

        //when&then
        val idDiff = restaurantPicture2.id - restaurantPicture.id
        assertThat(idDiff).isEqualTo(1L);
    }
}