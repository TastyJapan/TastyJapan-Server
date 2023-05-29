package com.tastyjapan.member.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MemberTest {
    private lateinit var member: Member

    @BeforeEach
    fun setup() {
        member = Member(
            name = "chocochip",
            email = "dev.chocochip@gmail.com",
            picture = "https://lh3.googleusercontent.com/ogw/AOLn63FIEWhJRLAtFKsz3zBacrt3-OlUmV5BkCBqbhvs=s64-c-mo",
            role = Role.USER
        )
    }

    @Test
    @DisplayName("유저를 만들 수 있다.")
    fun createUser() {
        assertEquals("chocochip", member.name)
        assertEquals("dev.chocochip@gmail.com", member.email)
        assertEquals("https://lh3.googleusercontent.com/ogw/AOLn63FIEWhJRLAtFKsz3zBacrt3-OlUmV5BkCBqbhvs=s64-c-mo", member.picture)
        assertEquals(Role.USER, member.role)
    }
    @Test
    fun testEquals() {
        val otherMember = Member(
            name = member.name,
            email = member.email,
            picture = member.picture,
            role = member.role
        )
        assertEquals(member, otherMember)
    }

    @Test
    fun testHashCode() {
        val otherMember = Member(
            id = member.id,
            name = member.name,
            email = member.email,
            picture = member.picture,
            role = member.role
        )
        assertEquals(member.hashCode(), otherMember.hashCode())
    }

    @Test
    fun testToString() {
        val expectedString = "Member(id=${member.id}, name='${member.name}', email='${member.email}', picture='${member.picture}', role=${member.role})"
        assertEquals(expectedString, member.toString())
    }

}
