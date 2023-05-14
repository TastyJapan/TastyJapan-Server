package com.tastyjapan.group.domain

import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GroupsTest {

    lateinit var groups: Groups

    @BeforeEach
    fun setUp() {
        groups = Groups(
            id = 1L,
            title = "My Favorite Restaurants",
        )
        val member = Member(1L, "John Doe", "johndoe@example.com", "picture.png", Role.USER)
        groups.member = member
    }

    @Test
    fun createSaveTest() {

        assertEquals(1L, groups.id)
        assertEquals("My Favorite Restaurants", groups.title)
        assertEquals(Member(1L, "John Doe", "johndoe@example.com", "picture.png", Role.USER), groups.member)
    }
}
