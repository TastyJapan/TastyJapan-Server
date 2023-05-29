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
    fun createGroup(memberId: Long, groupRequest: GroupRequest): Long {
        // member 존재 여부 체크
        val member = memberRepository.findById(memberId).orElseThrow {
            TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.USER_NOT_FOUND))
        }

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
    @Transactional
    fun addOneRestaurant(groupId: Long, restaurantId: Long): Long {
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