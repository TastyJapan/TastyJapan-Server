package com.tastyjapan.member.domain.repository

import com.tastyjapan.group.domain.Groups
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    lateinit var memberRepository: MemberRepository

    @BeforeEach
    fun setUp() {
        memberRepository.deleteAll()
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 그룹을 저장하면, 해당 ID의 그룹은 후에 작성된 그룹 정보로 업데이트 된다.")
    @Test
    fun saveSameId() {
        // given
        val member1 =
            Member(name = "John Doe", email = "johndoe@example.com", picture = "picture.png", role = Role.USER)
        memberRepository.save(member1)

        // when
        val member2 = Member(member1.id, "Mr Kiho", "johndoe@example.com", "picture.png", Role.USER)
        memberRepository.save(member2)

        // then
        Assertions.assertThat(memberRepository.findById(member1.id)).isEqualTo(memberRepository.findById(member2.id))

        val savedMembers = memberRepository.findAll()
        Assertions.assertThat(savedMembers.size).isEqualTo(1)

        val foundMember = memberRepository.findById(member1.id).get()
        Assertions.assertThat(foundMember.name).isEqualTo(member2.name)
    }

    @DisplayName("id가 없는 Member 엔티티를 저장하면 순차적으로 ID를 부여하여 저장한다.")
    @Test
    fun saveNoIdMember() {
        // given
        val member1 =
            Member(name = "John Doe", email = "johndoe@example.com", picture = "picture.png", role = Role.USER)
        memberRepository.save(member1)


        // when
        val member2 =
            Member(name = "John Doe2", email = "johndoe2@example.com", picture = "picture2.png", role = Role.USER)
        memberRepository.save(member2)

        // then
        val idDiff = member2.id - member1.id
        Assertions.assertThat(idDiff).isEqualTo(1L);
    }
}