package com.tastyjapan.member.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MemberTest {
    @Test
    @DisplayName("유저를 만들 수 있다.")
    fun createUser() {

        val id: Long = 1
        val name = "chocochip"
        val email = "dev.chocochip@gmail.com"
        val picture = "https://lh3.googleusercontent.com/ogw/AOLn63FIEWhJRLAtFKsz3zBacrt3-OlUmV5BkCBqbhvs=s64-c-mo"
        val role = Role.USER

        val member = Member(id, name, email, picture, role)

        assertEquals(id, member.id)
        assertEquals(name, member.name)
        assertEquals(email, member.email)
        assertEquals(role, member.role)
    }

}
