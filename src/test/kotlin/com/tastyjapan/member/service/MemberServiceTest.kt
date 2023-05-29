package com.tastyjapan.member.service

import com.tastyjapan.fixture.MemberFixture.createMemberChoco
import com.tastyjapan.fixture.MemberFixture.createMemberKyk
import com.tastyjapan.member.domain.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private lateinit var memberService: MemberService

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @BeforeEach
    fun setUp() {
        memberRepository.deleteAll()
    }

    @DisplayName("모든 사용자 조회에 성공한다.")
    @Test
    fun getAllMemberTest(){
        // given
        val member1 = createMemberChoco()
        val member2 = createMemberKyk()
        memberRepository.saveAll(listOf(member1, member2))

        // when
        val members = memberService.getAllMembers()

        // then
        assertThat(2).isEqualTo(members.size)
        assertThat(member1.name).isEqualTo(members[0].name)
        assertThat(member2.name).isEqualTo(members[1].name)
    }
}