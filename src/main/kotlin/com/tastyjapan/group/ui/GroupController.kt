package com.tastyjapan.group.ui

import com.tastyjapan.global.response.ApiResponse
import com.tastyjapan.global.response.ApiUtils
import com.tastyjapan.group.service.GroupService
import com.tastyjapan.group.ui.dto.GroupRequest
import com.tastyjapan.group.ui.dto.GroupRestaurantsUpdateRequest
import com.tastyjapan.group.ui.dto.GroupWithRestaurantResponse
import com.tastyjapan.group.ui.dto.GroupsResponse
import io.swagger.annotations.ApiOperation
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
class GroupController(val groupService: GroupService) {
    /**
     * 그룹을 생성합니다.w
     */
    @PostMapping("/user/{user-id}")
    fun createGroup(
        @PathVariable("user-id") userId: Long, @RequestBody groupRequest: GroupRequest
    ): ResponseEntity<ApiResponse<Long>> {
        val result = groupService.createGroup(userId, groupRequest)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    /**
     * 식당을 하나 추가합니다.
     */
    @PostMapping("/{group-id}/restaurants/{restaurant-id}")
    fun addOneRestaurant(
        @PathVariable("group-id") groupId: Long,
        @PathVariable("restaurant-id") restaurantId: Long
    ): ResponseEntity<ApiResponse<Long>> {
        val result = groupService.addOneRestaurant(groupId, restaurantId)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    /**
     * 그룹들을 반환합니다.
     */
    @GetMapping("/{user-id}")
    fun getGroups(@PathVariable("user-id") userId: Long): ResponseEntity<ApiResponse<List<GroupsResponse>>> {
        val result = groupService.getGroups(userId)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    /**
     * 그룹의 식당 리스트를 반환합니다.
     */
    @GetMapping("/{group-id}/restaurants")
    fun getGroupRestaurants(@PathVariable("group-id") groupId: Long): ResponseEntity<ApiResponse<GroupWithRestaurantResponse>> {
        val result = groupService.getGroupRestaurants(groupId)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

}