package com.tastyjapan.group.domain

import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GroupTest {

    lateinit var group: Group

    @BeforeEach
    fun setUp() {
        group = Group(
            id = 1L,
            title = "My Favorite Restaurants",
        )
        val member = Member(1L, "John Doe", "johndoe@example.com", "picture.png", Role.USER)
        group.member = member
    }

    @Test
    fun createSaveTest() {

        assertEquals(1L, group.id)
        assertEquals("My Favorite Restaurants", group.title)
        assertEquals(Member(1L, "John Doe", "johndoe@example.com", "picture.png", Role.USER), group.member)
    }
}
