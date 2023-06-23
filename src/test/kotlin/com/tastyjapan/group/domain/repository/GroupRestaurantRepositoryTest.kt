package com.tastyjapan.group.domain.repository

import com.tastyjapan.fixture.MemberFixture.createMemberChoco
import com.tastyjapan.fixture.RestaurantFixture.createRestaurantRamen
import com.tastyjapan.group.domain.GroupRestaurant
import com.tastyjapan.group.domain.Groups
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.restaurant.domain.repository.RestaurantRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class GroupRestaurantRepositoryTest {
    @Autowired
    lateinit var groupRestaurantRepository: GroupRestaurantRepository

    @Autowired
    lateinit var groupRepository: GroupRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @BeforeEach
    fun setUp() {
        groupRestaurantRepository.deleteAll()
        groupRepository.deleteAll()
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 그룹을 저장하면, 해당 ID의 그룹은 후에 작성된 그룹 정보로 업데이트 된다.")
    @Test
    fun saveSameId() {
        // given
        val member1 = createMemberChoco()
        memberRepository.save(member1)

        val groups1 = Groups(title = "group1")
        groups1.member = member1
        groupRepository.save(groups1)

        val restaurant1 = createRestaurantRamen()
        restaurantRepository.save(restaurant1)

        val groupRestaurant1 = GroupRestaurant(groups = groups1, restaurants = restaurant1)
        groupRestaurantRepository.save(groupRestaurant1)

        // when
        val member2 = createMemberChoco()
        memberRepository.save(member2)

        val groups2 = Groups(title = "group2")
        groups2.member = member2
        groupRepository.save(groups2)

        val restaurant2 = createRestaurantRamen()
        restaurantRepository.save(restaurant2)

        val groupRestaurant2 = GroupRestaurant(id = groupRestaurant1.id, groups = groups2, restaurants = restaurant2)
        groupRestaurantRepository.save(groupRestaurant2)

        // then
        val savedGroupRestaurant = groupRestaurantRepository.findAll()
        Assertions.assertThat(savedGroupRestaurant.size).isEqualTo(1)

        val foundGroup = groupRepository.findById(groups1.id).get()
        Assertions.assertThat(foundGroup.title).isEqualTo(groups1.title)
    }

    @DisplayName("id가 없는 Group 엔티티를 저장하면 순차적으로 ID를 부여하여 저장한다.")
    @Test
    fun saveNoIdGroup() {
        // given
        val member1 = createMemberChoco()
        memberRepository.save(member1)

        val groups1 = Groups(title = "group1")
        groups1.member = member1
        groupRepository.save(groups1)

        val restaurant1 = createRestaurantRamen()
        restaurantRepository.save(restaurant1)

        val groupRestaurant1 = GroupRestaurant(groups = groups1, restaurants = restaurant1)
        groupRestaurantRepository.save(groupRestaurant1)

        // when
        val member2 = createMemberChoco()
        memberRepository.save(member2)

        val groups2 = Groups(title = "group2")
        groups2.member = member2
        groupRepository.save(groups2)

        val restaurant2 = createRestaurantRamen()
        restaurantRepository.save(restaurant2)

        val groupRestaurant2 = GroupRestaurant(groups = groups2, restaurants = restaurant2)
        groupRestaurantRepository.save(groupRestaurant2)

        // then
        val idDiff = groupRestaurant2.id - groupRestaurant1.id
        Assertions.assertThat(idDiff).isEqualTo(1L);
    }
}