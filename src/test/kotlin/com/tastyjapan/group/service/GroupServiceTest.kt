package com.tastyjapan.group.service

import com.tastyjapan.fixture.MemberFixture.createMemberChoco
import com.tastyjapan.fixture.RestaurantFixture.createRestaurantRamen
import com.tastyjapan.fixture.RestaurantFixture.createRestaurantSushi
import com.tastyjapan.group.domain.GroupRestaurant
import com.tastyjapan.group.domain.Groups
import com.tastyjapan.group.domain.repository.GroupRepository
import com.tastyjapan.group.domain.repository.GroupRestaurantRepository
import com.tastyjapan.group.ui.dto.GroupRequest
import com.tastyjapan.group.ui.dto.GroupRestaurantsUpdateRequest
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.restaurant.domain.Restaurant
import com.tastyjapan.restaurant.domain.repository.RestaurantRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest
class GroupServiceTest {
    @Autowired
    private lateinit var groupService: GroupService

    @Autowired
    private lateinit var groupRestaurantRepository: GroupRestaurantRepository

    @Autowired
    private lateinit var groupRepository: GroupRepository

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Autowired
    private lateinit var restaurantRepository: RestaurantRepository

    private lateinit var member: Member
    private lateinit var restaurant1: Restaurant
    private lateinit var restaurant2: Restaurant

    @BeforeEach
    fun setUp() {
        memberRepository.deleteAll()
        groupRepository.deleteAll()
        groupRestaurantRepository.deleteAll()

        member = createMemberChoco()
        memberRepository.save(member)

        restaurant1 = createRestaurantRamen()
        restaurant2 = createRestaurantSushi()
        restaurantRepository.saveAll(listOf(restaurant1, restaurant2))
    }

    @Test
    @DisplayName("그룹을 생성한다.")
    fun createGroup() {
        // given
        val groupRequest = GroupRequest(title = "테스트 그룹", restaurantList = listOf(restaurant1.id, restaurant2.id))

        // when
        val groupId = groupService.createGroup(memberId = member.id, groupRequest = groupRequest)

        // then
        assertThat(groupRepository.findAll().get(0).id).isEqualTo(groupId)
    }
