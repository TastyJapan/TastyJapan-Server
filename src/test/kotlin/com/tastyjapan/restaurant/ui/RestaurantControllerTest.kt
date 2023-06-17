package com.tastyjapan.restaurant.ui

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.tastyjapan.city.City
import com.tastyjapan.fixture.RestaurantFixture.createRestaurantRamen
import com.tastyjapan.global.response.ApiResponse
import com.tastyjapan.global.response.ApiUtils
import com.tastyjapan.restaurant.domain.repository.RestaurantRepository
import com.tastyjapan.restaurant.service.RestaurantService
import com.tastyjapan.restaurant.ui.dto.RestaurantDetailResponse
import com.tastyjapan.restaurant.ui.dto.RestaurantResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
class RestaurantControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var restaurantService: RestaurantService

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        mockMvc = MockMvcBuilders.standaloneSetup(RestaurantController(restaurantService))
            .setCustomArgumentResolvers(PageableHandlerMethodArgumentResolver())
            .build()
    }

    private companion object {
        private val restaurantResponse: RestaurantResponse = RestaurantResponse(createRestaurantRamen())
        val city = "tokyo"
        val menu = "ramen"
        val latMax = createRestaurantRamen().latitude + 0.1
        val latMin = createRestaurantRamen().latitude - 0.1
        val longMax = createRestaurantRamen().longitude + 0.1
        val longMin = createRestaurantRamen().longitude - 0.1
        val pageable = PageRequest.of(0, 10)
    }

    @Test
    fun `Test getRestaurants endpoint`() {
        // Given
        val slice: Slice<RestaurantResponse> = SliceImpl(listOf(restaurantResponse), pageable, false)
        every {
            restaurantService.getRestaurants(
                city = city,
                menu = menu,
                longMax = longMax,
                longMin = longMin,
                latMax = latMax,
                latMin = latMin,
                pageable = pageable
            )
        } returns slice

        // When & Then
        mockMvc.get("/api/v1/restaurants") {
            contentType = MediaType.APPLICATION_JSON
            param("city", city)
            param("menu", menu)
            param("latMax", latMax.toString())
            param("latMin", latMin.toString())
            param("longMax", longMax.toString())
            param("longMin", longMin.toString())
            param("page", "0")
            param("size", "10")
        }.andExpect {
            status().isOk
        }.andExpect {
            jsonPath("\$.success") { value(true) }
            jsonPath("\$.response.content[0].id") { value(restaurantResponse.id) }
        }.andDo {
            verify {
                restaurantService.getRestaurants(
                    city = city,
                    menu = menu,
                    longMax = longMax,
                    longMin = longMin,
                    latMax = latMax,
                    latMin = latMin,
                    pageable = pageable
                )
            }
        }
    }

    @DisplayName("Get restaurant detail")
    @Test
    fun getRestaurantDetail() {
        // Given
        val restaurantDetailResponse = RestaurantDetailResponse(createRestaurantRamen())

        every { restaurantService.getRestaurantDetail(restaurantDetailResponse.id) } returns restaurantDetailResponse

        // When & Then
        mockMvc.get("/api/v1/restaurants/${restaurantDetailResponse.id}/detail") {
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status().isOk
        }.andExpect {
            jsonPath("\$.success") { value(true) }
            jsonPath("\$.response.id") { value(restaurantDetailResponse.id) }
        }.andDo {
            verify {
                restaurantService.getRestaurantDetail(restaurantDetailResponse.id)
            }
        }
    }
}