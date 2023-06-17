package com.tastyjapan.menu.ui

import com.tastyjapan.global.response.ApiResponse
import com.tastyjapan.global.response.ApiUtils
import com.tastyjapan.menu.service.MenuService
import com.tastyjapan.menu.ui.dto.MenuResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
class MenuController (val menuService: MenuService) {

    @GetMapping("/restaurants/{restaurant-id}")
    fun getMenusByRestaurant(
        @PathVariable("restaurant-id") restaurantId: Long
    ): ResponseEntity<ApiResponse<List<MenuResponse>>> {
        val result = menuService.getMenuByRestaurantId(restaurantId)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }
}