package com.tastyjapan.group.service

import com.tastyjapan.exception.ErrorType
import com.tastyjapan.exception.ExceptionResponse
import com.tastyjapan.exception.InvalidArgumentException
import com.tastyjapan.exception.TastyJapanException
import com.tastyjapan.group.domain.Groups
import com.tastyjapan.group.domain.GroupRestaurant
import com.tastyjapan.group.domain.repository.GroupRepository
import com.tastyjapan.group.domain.repository.GroupRestaurantRepository
import com.tastyjapan.group.mapper.GroupMapper
import com.tastyjapan.group.ui.dto.GroupRequest
import com.tastyjapan.group.ui.dto.GroupRestaurantsUpdateRequest
import com.tastyjapan.group.ui.dto.GroupWithRestaurantResponse
import com.tastyjapan.group.ui.dto.GroupsResponse
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.restaurant.domain.repository.RestaurantRepository
import com.tastyjapan.restaurant.ui.dto.RestaurantResponse
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

const val MAX_RESTAURANT_SIZE: Int = 10
const val MAX_GROUP_SIZE: Int = 10

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
class GroupService(
    val groupRepository: GroupRepository,
    val memberRepository: MemberRepository,
    val restaurantRepository: RestaurantRepository,
    val groupRestaurantRepository: GroupRestaurantRepository,
    val invalidArgumentException: InvalidArgumentException
) {
    @Transactional
    fun createGroup(memberId: Long, groupRequest: GroupRequest, member: Member): Long {
        // member 존재 여부 체크
        invalidArgumentException.checkMemberId(memberId = memberId, member = member)
        // group count 체크
        countGroupCount(memberId)

        // restaurant count 체크
        countRestaurantCount(groupRequest.restaurantList)

        // group 생성
        val groups = Groups(title = groupRequest.title)
        groups.addMember(member)
        groupRepository.save(groups)
        var groupRestaurantList = mutableListOf<GroupRestaurant>()

        groupRequest.restaurantList.forEach { restaurantId ->
            val restaurant = restaurantRepository.findById(restaurantId).orElseThrow {
                TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.RESTAURANT_NOT_FOUND))
            }
            // groupRestaurant 생성
            val groupRestaurant = GroupRestaurant(groups = groups, restaurants = restaurant)
            // groupRestaurantList에 추가
            groupRestaurantList.add(groupRestaurant)

            // restaurant 추가
            restaurant.addGroupRestaurant(groupRestaurant)
            restaurantRepository.save(restaurant)
            groupRestaurantRepository.save(groupRestaurant)
        }

        groups.groupRestaurantList = groupRestaurantList
        return groups.id
    }

    fun getGroups(memberId: Long): List<GroupsResponse> {
        return groupRepository.findGroupsByMemberId(memberId).stream()
            .map { group -> GroupMapper.INSTANCE.groupEntityToResponse(group) }
            .collect(Collectors.toList())
    }

    fun getGroupRestaurants(groupId: Long): GroupWithRestaurantResponse {
        val groupRestaurantList = groupRestaurantRepository.findGroupRestaurantByGroupId(groupId)
        if (groupRestaurantList.isEmpty()) {
            throw TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.GROUP_NOT_FOUND))
        }

        var restaurantResult = mutableListOf<RestaurantResponse>()

        groupRestaurantList.forEach() { groupRestaurant ->
            val restaurant = groupRestaurant.restaurants
            restaurantResult.add(RestaurantResponse(restaurant))
        }

        return GroupWithRestaurantResponse(
            title = groupRestaurantList[0].groups.title,
            restaurantList = restaurantResult
        )
    }

    @Transactional
    fun updateGroupTitle(groupId: Long, title: String, member: Member): Long {
        invalidArgumentException.checkGroup(groupId = groupId, member = member)
        return groupRepository.updateGroupTitle(groupId, title)
    }

    @Transactional
    fun updateGroupRestaurants(
        groupId: Long,
        groupRestaurantsUpdateRequest: GroupRestaurantsUpdateRequest,
        member: Member
    ): Long {
        invalidArgumentException.checkGroup(groupId = groupId, member = member)
        countRestaurantCount(groupRestaurantsUpdateRequest.restaurantList)

        // 1. Group 조회
        val group = groupRepository.findById(groupId)
            .orElseThrow { TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.GROUP_NOT_FOUND)) }

        // 2. GroupRestaurant들 삭제 +  Restaurant에서 GroupRestaurant 삭제
        groupRestaurantRepository.findGroupRestaurantByGroupId(groupId).forEach { groupRestaurant ->
            //  Restaurant에서 GroupRestaurant 제거
            val restaurant = groupRestaurant.restaurants
            restaurant.groupRestaurantList.remove(groupRestaurant)

            // GroupRestaurant 삭제
            restaurantRepository.save(restaurant)
            groupRestaurantRepository.delete(groupRestaurant)

            group.groupRestaurantList.remove(groupRestaurant)
        }

        // 3. GroupRestaurant 생성 + Restaurant에 GroupRestaurant 추가
        groupRestaurantsUpdateRequest.restaurantList.forEach() { restaurantId ->
            val restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow {
                    TastyJapanException(
                        HttpStatus.BAD_REQUEST,
                        ExceptionResponse(ErrorType.RESTAURANT_NOT_FOUND)
                    )
                }

            // groupRestaurant 생성
            val groupRestaurant = GroupRestaurant(groups = group, restaurants = restaurant)
            // groupRestaurantList에 추가
            group.groupRestaurantList.add(groupRestaurant)

            // restaurant 추가
            restaurant.addGroupRestaurant(groupRestaurant)
            restaurantRepository.save(restaurant)
            groupRestaurantRepository.save(groupRestaurant)
        }
        return group.id
    }

    @Transactional
    fun deleteGroup(groupId: Long, member: Member): Boolean {
        invalidArgumentException.checkGroup(groupId = groupId, member = member)
        groupRestaurantRepository.findGroupRestaurantByGroupId(groupId).forEach { groupRestaurant ->
            //  Restaurant에서 GroupRestaurant 제거
            val restaurant = groupRestaurant.restaurants
            restaurant.groupRestaurantList.remove(groupRestaurant)

            // GroupRestaurant 삭제
            restaurantRepository.save(restaurant)
            groupRestaurantRepository.delete(groupRestaurant)
        }
        // Group 삭제
        groupRepository.deleteGroup(groupId)
        return true
    }

    @Transactional
    fun addOneRestaurant(groupId: Long, restaurantId: Long, member: Member): Long {
        invalidArgumentException.checkGroup(groupId = groupId, member = member)
        val restaurant = restaurantRepository.findById(restaurantId).orElseThrow {
            TastyJapanException(
                HttpStatus.BAD_REQUEST,
                ExceptionResponse(ErrorType.RESTAURANT_NOT_FOUND)
            )
        }
        val groups = groupRepository.findById(groupId).orElseThrow {
            TastyJapanException(
                HttpStatus.BAD_REQUEST,
                ExceptionResponse(ErrorType.GROUP_NOT_FOUND)
            )
        }

        val groupRestaurant = GroupRestaurant(groups = groups, restaurants = restaurant)

        restaurant.addGroupRestaurant(groupRestaurant)
        groups.addGroupRestaurant(groupRestaurant)

        groupRestaurantRepository.save(groupRestaurant)
        return groupId
    }

    /**
     *
     */
    private fun countGroupCount(memberId: Long) {
        if (groupRepository.countGroupsByMemberId(memberId) >= MAX_GROUP_SIZE) {
            throw TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.GROUP_COUNT_EXCEEDED))
        }
    }

    /**
     *
     */
    private fun countRestaurantCount(restaurantList: List<Long>) {
        // restaurant count 체크
        if (restaurantList.size > MAX_RESTAURANT_SIZE) {
            throw TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.RESTAURANT_COUNT_EXCEEDED))
        }
    }

}