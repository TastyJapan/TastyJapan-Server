package com.tastyjapan.group.ui

import com.tastyjapan.global.annotation.CurrentMember
import com.tastyjapan.global.response.ApiResponse
import com.tastyjapan.global.response.ApiUtils
import com.tastyjapan.group.service.GroupService
import com.tastyjapan.group.ui.dto.GroupRequest
import com.tastyjapan.group.ui.dto.GroupRestaurantsUpdateRequest
import com.tastyjapan.group.ui.dto.GroupWithRestaurantResponse
import com.tastyjapan.group.ui.dto.GroupsResponse
import com.tastyjapan.member.domain.Member
import io.swagger.annotations.ApiOperation
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
class GroupController(val groupService: GroupService) {
    /**
     * 그룹을 생성합니다.
     */
    @PostMapping("/")
    fun createGroup(
        @CurrentMember member: Member, @RequestBody groupRequest: GroupRequest
    ): ResponseEntity<ApiResponse<Long>> {
        val result = groupService.createGroup(member.id, groupRequest)
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
    @GetMapping("/")
    fun getGroups(@CurrentMember member: Member): ResponseEntity<ApiResponse<List<GroupsResponse>>> {
        val result = groupService.getGroups(member.id)
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


    /**
     * 그룹을 제목을 수정합니다.
     */
    @PutMapping("/{group-id}/title")
    fun updateGroupTitle(
        @PathVariable("group-id") groupId: Long, @RequestBody title: String
    ): ResponseEntity<ApiResponse<Long>> {
        val result = groupService.updateGroupTitle(groupId, title)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    /**
     * 그룹의 식당 목록을 수정합니다.
     */
    @PutMapping("/{group-id}/restaurants")
    fun updateGroupRestaurants(
        @PathVariable("group-id") groupId: Long,
        @RequestBody groupRestaurantsUpdateRequest: GroupRestaurantsUpdateRequest
    ): ResponseEntity<ApiResponse<Long>> {
        val result = groupService.updateGroupRestaurants(groupId, groupRestaurantsUpdateRequest)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    /**
     * 그룹을 삭제합니다.
     */
    @DeleteMapping("/{group-id}")
    fun deleteGroup(@PathVariable("group-id") groupId: Long): ResponseEntity<ApiResponse<Boolean>> {
        val result = groupService.deleteGroup(groupId)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }
}