package com.tastyjapan.menu.service

import com.tastyjapan.fixture.MenuFixture.createFirstRamenMenu
import com.tastyjapan.fixture.MenuFixture.createSushiMenu
import com.tastyjapan.fixture.RestaurantFixture.createRestaurantRamen
import com.tastyjapan.menu.domain.repository.MenuRepository
import com.tastyjapan.restaurant.domain.Restaurant
import com.tastyjapan.restaurant.domain.repository.RestaurantRepository
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class MenuServiceTest {
    @Autowired
    private lateinit var menuService: MenuService

    @Autowired
    private lateinit var menuRepository: MenuRepository

    @Autowired
    private lateinit var restaurantRepository: RestaurantRepository

    private lateinit var restaurant: Restaurant
    @BeforeEach
    fun setUp() {
        menuRepository.deleteAll()
        restaurantRepository.deleteAll()

        restaurant = createRestaurantRamen()
        restaurantRepository.save(restaurant)
    }

    @Test
    fun getMenuByRestaurantIdTest() {
        // given
        val menu1 = createFirstRamenMenu()
        val menu2 = createSushiMenu()
        restaurant.addMenu(menu1)
        restaurant.addMenu(menu2)
        menuRepository.saveAll(listOf(menu1, menu2))

        // when
        val menus = menuService.getMenuByRestaurantId(restaurant.id)

        // then
        assertEquals(2, menus.size)
        assertEquals(menu1.name, menus[0].name)
        assertEquals(menu2.name, menus[1].name)
    }
}