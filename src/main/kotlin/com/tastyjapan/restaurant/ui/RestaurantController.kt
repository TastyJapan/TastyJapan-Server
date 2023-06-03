package com.tastyjapan.restaurant.ui

import com.tastyjapan.global.response.ApiResponse
import com.tastyjapan.global.response.ApiUtils
import com.tastyjapan.member.ui.dto.MemberResponse
import com.tastyjapan.restaurant.service.RestaurantService
import com.tastyjapan.restaurant.ui.dto.RestaurantDetailResponse
import com.tastyjapan.restaurant.ui.dto.RestaurantResponse
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
class RestaurantController(val restaurantService: RestaurantService) {

    @GetMapping
    fun getRestaurants(
        @RequestParam(required = false) city: String?,
        @RequestParam(required = false) menu: String?,
        @RequestParam latMax: Double,
        @RequestParam latMin: Double,
        @RequestParam longMax: Double,
        @RequestParam longMin: Double,
        pageable: Pageable
    ): ResponseEntity<ApiResponse<Slice<RestaurantResponse>>> {
        val result = restaurantService.getRestaurants(
            city = city,
            menu = menu,
            latMax = latMax,
            latMin = latMin,
            longMax = longMax,
            longMin = longMin,
            pageable = pageable
        )
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    @GetMapping("/{restaurant-id}/detail")
    fun getRestaurantDetail(
        @PathVariable("restaurant-id") restaurantId: Long
    ): ResponseEntity<ApiResponse<RestaurantDetailResponse>> {
        val result = restaurantService.getRestaurantDetail(restaurantId)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    @GetMapping("/recommend")
    fun getRestaurantRecommend(): ResponseEntity<ApiResponse<RestaurantResponse>> {
        val result = restaurantService.getRestaurantRecommend()
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }
}