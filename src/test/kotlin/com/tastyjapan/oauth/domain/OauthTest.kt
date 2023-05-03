package com.tastyjapan.oauth.domain

import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OauthTest {

    lateinit var oauth: Oauth
    lateinit var member: Member

    @BeforeEach
    fun setUp() {
        member = Member(1L, "John Doe", "johndoe@example.com", "picture.png", Role.USER)

        oauth = Oauth(
            id = 1L,
            member = member,
            provider = OauthProvider.GOOGLE
        )
    }

    @Test
    fun testGetters() {
        assertEquals(1L, oauth.id)
        assertEquals(member, oauth.member)
        assertEquals(OauthProvider.GOOGLE, oauth.provider)
        assertEquals("tasty", oauth.refreshToken)
    }

}
