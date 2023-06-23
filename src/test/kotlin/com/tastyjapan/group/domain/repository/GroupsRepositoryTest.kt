package com.tastyjapan.group.domain.repository

import com.tastyjapan.city.City
import com.tastyjapan.group.domain.Groups
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.restaurant.domain.Restaurant
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class GroupsRepositoryTest {
    @Autowired
    lateinit var groupRepository: GroupRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    lateinit var groups1: Groups
    lateinit var member: Member

    @BeforeEach
    fun setUp() {
        memberRepository.deleteAll()
        groupRepository.deleteAll()
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 그룹을 저장하면, 해당 ID의 그룹은 후에 작성된 그룹 정보로 업데이트 된다.")
    @Test
    fun saveSameId() {
        // given
        member = Member(name = "John Doe", email = "johndoe@example.com", picture = "picture.png", role = Role.USER)
        memberRepository.save(member)

        groups1 = Groups(title = "group1")
        groups1.member = member
        groupRepository.save(groups1)

        // then
        val savedGroups = groupRepository.findAll()
        assertThat(savedGroups.size).isEqualTo(1)

        val foundGroup = groupRepository.findById(groups1.id).get()
        assertThat(foundGroup.title).isEqualTo(groups1.title)
    }

    @DisplayName("id가 없는 Group 엔티티를 저장하면 순차적으로 ID를 부여하여 저장한다.")
    @Test
    fun saveNoIdGroup() {
        // given
        member = Member(name = "John Doe", email = "johndoe@example.com", picture = "picture.png", role = Role.USER)
        memberRepository.save(member)

        groups1 = Groups(title = "group1")
        groups1.member = member
        groupRepository.save(groups1)

        // when
        val group2 = Groups(
            title = "group2"
        )
        group2.member = member
        groupRepository.save(group2)

        // then
        val idDiff = group2.id - groups1.id
        assertThat(idDiff).isEqualTo(1L);
    }
}