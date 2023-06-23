package com.tastyjapan.oauth.ui

import com.fasterxml.jackson.databind.ObjectMapper
import com.tastyjapan.ApiControllerTest
import com.tastyjapan.oauth.service.AuthService
import com.tastyjapan.oauth.ui.dto.SignInResponse
import com.tastyjapan.oauth.ui.dto.SignUpRequest
import com.tastyjapan.security.custom.JwtTokens
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.springframework.boot.test.mock.mockito.MockBean

import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest : ApiControllerTest(uri = "/api/v1/auth") {

    @MockBean
    private lateinit var authService: AuthService

    private val jwtTokens = JwtTokens("access-token", "refresh-token")


    @Test
    fun `기가입자가 아닌 경우 로그인`() {
        `when`(authService.signIn("code")).thenReturn(SignInResponse(isMember = false, JwtTokens()))

        val uri = "$uri/signin"
        mockMvc.perform(
            RestDocumentationRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("auth-code", "code")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
            .andDo(MockMvcResultHandlers.print())
            .andDo(
                MockMvcRestDocumentation.document(
                    "signin-not-member",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.beneathPath("response").withSubsectionId("response"),
                        *signInNotMemberPreviewDataResponseFieldsSnippet(),
                    ).andWithPrefix("jwtTokens[].", *jwtTokensPreviewDataResponseFieldsSnippet())
                )
            )
    }

    @Test
    fun `기가입자인 경우 로그인`() {
        `when`(authService.signIn("code")).thenReturn(SignInResponse(isMember = true, jwtTokens))

        val uri = "$uri/signin"
        mockMvc.perform(
            RestDocumentationRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("auth-code", "code")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value("true"))
            .andDo(MockMvcResultHandlers.print())
            .andDo(
                MockMvcRestDocumentation.document(
                    "signin",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.beneathPath("response").withSubsectionId("response"),
                        *signInNotMemberPreviewDataResponseFieldsSnippet(),
                    ).andWithPrefix("jwtTokens[].", *jwtTokensPreviewDataResponseFieldsSnippet())
                )
            )
    }

    private fun jwtTokensPreviewDataResponseFieldsSnippet(): Array<FieldDescriptor> {
        return arrayOf(
            PayloadDocumentation.fieldWithPath("accessToken").description("엑세스 토큰").optional()
                .type(JsonFieldType.STRING),
            PayloadDocumentation.fieldWithPath("refreshToken").description("엑세스 토큰을 재발급 받기 위한 리프레쉬 토큰").optional()
                .type(JsonFieldType.STRING),
        )
    }

    private fun signInNotMemberPreviewDataResponseFieldsSnippet(): Array<FieldDescriptor> {
        return arrayOf(
            PayloadDocumentation.fieldWithPath("isMember").description("이미 가입을 했다면 true, 하지 않았다면 false"),
            PayloadDocumentation.subsectionWithPath("jwtTokens").description("엑세스, 리프레쉬 토큰"),
        )
    }

}