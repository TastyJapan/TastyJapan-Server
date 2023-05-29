package com.tastyjapan.group.ui

import com.tastyjapan.fixture.RestaurantFixture.createRestaurantRamen
import com.tastyjapan.global.response.ApiUtils
import com.tastyjapan.group.service.GroupService
import com.tastyjapan.group.ui.dto.GroupRequest
import com.tastyjapan.group.ui.dto.GroupRestaurantsUpdateRequest
import com.tastyjapan.group.ui.dto.GroupWithRestaurantResponse
import com.tastyjapan.group.ui.dto.GroupsResponse
import com.tastyjapan.restaurant.ui.dto.RestaurantResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
class GroupControllerTest {

    @Test
    fun createGroup() {
        val groupService = mockk<GroupService>()

        // Create an instance of GroupController and pass the mock GroupService
        val groupController = GroupController(groupService)

        // Define the input parameters for the createGroup endpoint
        val userId = 123L
        val groupRequest = GroupRequest(title = "My Group", restaurantList = listOf(createRestaurantRamen().id))

        // Define the expected response
        val expectedGroupId = createRestaurantRamen().id
        val expectedResponse = ResponseEntity.ok(ApiUtils.success(expectedGroupId))

        // Mock the behavior of the GroupService's createGroup method
        every {
            groupService.createGroup(userId, groupRequest)
        } returns expectedGroupId

        // Call the createGroup endpoint
        val response = groupController.createGroup(userId, groupRequest)

        // Verify the response
        assertEquals(expectedResponse.statusCode, response.statusCode)
        assertEquals((expectedResponse.body?.getResponse() ?: "null"), (response.body?.getResponse() ?: "null"))
    }

    @Test
    fun getGroups() {
        val groupService = mockk<GroupService>()
        val groupController = GroupController(groupService)
        val userId = 123L

        val expectedGroupsResponse = listOf(
            GroupsResponse(id = 1L, title = "Group 1"),
            GroupsResponse(id = 2L, title = "Group 2")
        )
        val expectedResponse = ResponseEntity.ok(ApiUtils.success(expectedGroupsResponse))

        every {
            groupService.getGroups(userId)
        } returns expectedGroupsResponse

        val response = groupController.getGroups(userId)

        assertEquals(expectedResponse.statusCode, response.statusCode)
        assertEquals(expectedResponse.body?.getResponse(), response.body?.getResponse())
    }

    @Test
    fun getGroupRestaurants() {
        val groupService = mockk<GroupService>()
        val groupController = GroupController(groupService)
        val groupId = 123L

        val expectedGroupWithRestaurantResponse = GroupWithRestaurantResponse(
            title = "group", restaurantList = listOf(
                RestaurantResponse(createRestaurantRamen())
            )
        )
        val expectedResponse =
            ResponseEntity.ok(ApiUtils.success(expectedGroupWithRestaurantResponse))

        every {
            groupService.getGroupRestaurants(groupId)
        } returns expectedGroupWithRestaurantResponse

        val response = groupController.getGroupRestaurants(groupId)

        assertEquals(expectedResponse.statusCode, response.statusCode)
        assertEquals(expectedResponse.body?.getResponse(), response.body?.getResponse())
    }

    @Test
    fun updateGroupTitle() {
        val groupService = mockk<GroupService>()
        val groupController = GroupController(groupService)
        val groupId = 123L
        val newTitle = "Updated Group Title"

        val expectedUpdatedGroupId = 123L
        val expectedResponse = ResponseEntity.ok(ApiUtils.success(expectedUpdatedGroupId))

        every {
            groupService.updateGroupTitle(groupId, newTitle)
        } returns expectedUpdatedGroupId

        val response = groupController.updateGroupTitle(groupId, newTitle)

        assertEquals(expectedResponse.statusCode, response.statusCode)
        assertEquals(expectedResponse.body?.getResponse(), response.body?.getResponse())
    }

    @Test
    fun updateGroupRestaurants() {
        val groupService = mockk<GroupService>()
        val groupController = GroupController(groupService)
        val groupId = 123L
        val groupRestaurantsUpdateRequest = GroupRestaurantsUpdateRequest(restaurantList = listOf(1L, 2L, 3L))

        val expectedUpdatedGroupId = 123L
        val expectedResponse = ResponseEntity.ok(ApiUtils.success(expectedUpdatedGroupId))

        every {
            groupService.updateGroupRestaurants(groupId, groupRestaurantsUpdateRequest)
        } returns expectedUpdatedGroupId

        val response = groupController.updateGroupRestaurants(groupId, groupRestaurantsUpdateRequest)

        assertEquals(expectedResponse.statusCode, response.statusCode)
        assertEquals(expectedResponse.body?.getResponse(), response.body?.getResponse())
    }

    @Test
    fun addOneRestaurant(){
        val groupService = mockk<GroupService>()
        val groupController = GroupController(groupService)
        val groupId = 123L
        val restaurantId = 456L

        val expectedAdd = 123L
        val expectedResponse = ResponseEntity.ok(ApiUtils.success(expectedAdd))

        every { groupService.addOneRestaurant(groupId,restaurantId ) }returns expectedAdd

        val response = groupController.addOneRestaurant(groupId, restaurantId)

        assertEquals(expectedResponse.statusCode, response.statusCode)
        assertEquals(expectedResponse.body?.getResponse(), response.body?.getResponse())
    }
}