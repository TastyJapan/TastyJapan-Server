package com.tastyjapan.member.ui

import com.ninjasquad.springmockk.MockkBean
import com.tastyjapan.fixture.MemberFixture
import com.tastyjapan.global.response.ApiUtils
import com.tastyjapan.member.service.MemberService
import com.tastyjapan.member.ui.dto.MemberResponse
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
class MemberControllerTest {

    @Test
    fun getAllUsers() {
        val memberService = mockk<MemberService>()
        val memberController = MemberController(memberService)

        val memberId = 1L
        val memberName = "name"
        val email = "email"
        val picture = "myPicture"
        val expectedMemberList = mutableListOf<MemberResponse>(
            MemberResponse(
                id = memberId,
                name = memberName,
                email = email,
                picture = picture
            )
        )
        val expectedAnswer = ResponseEntity.ok(ApiUtils.success(expectedMemberList))
        every { memberService.getAllMembers()} returns expectedMemberList

        val response = memberController.getAllUsers()

        assertThat(expectedAnswer.statusCode).isEqualTo(response.statusCode)
        assertThat(expectedAnswer.body?.getResponse()).isEqualTo(response.body?.getResponse())
    }

    @Test
    fun getUser() {
        val memberService = mockk<MemberService>()
        val memberController = MemberController(memberService)

        val member = MemberFixture.createMemberChoco()
        val expectedMemberList = MemberResponse(
                id = member.id,
                name = member.name,
                email = member.email,
                picture = member.picture?:""
            )

        val expectedAnswer = ResponseEntity.ok(ApiUtils.success(expectedMemberList))
        every { memberService.getMember(memberId = member.id)} returns expectedMemberList

        val response = memberController.getUser(member, member.id)

        assertThat(expectedAnswer.statusCode).isEqualTo(response.statusCode)
        assertThat(expectedAnswer.body?.getResponse()).isEqualTo(response.body?.getResponse())
    }
}