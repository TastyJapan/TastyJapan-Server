package com.tastyjapan.group.domain

import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GroupsTest {

    private lateinit var groups: Groups
    private lateinit var member: Member
    private val title = "Group Title"

    @BeforeEach
    fun setUp() {
        groups = Groups(
            id = 1L,
            title = title,
        )
        member = Member(1L, "John Doe", "johndoe@example.com", "picture.png", Role.USER)
        groups.member = member
    }

    @Test
    fun createSaveTest() {

        assertEquals(1L, groups.id)
        assertEquals(title, groups.title)
        assertEquals(Member(1L, "John Doe", "johndoe@example.com", "picture.png", Role.USER), groups.member)
    }


    @Test
    fun testEquals() {
        val otherGroups = Groups(id = groups.id, title = title)
        assertEquals(groups, otherGroups)
    }

    @Test
    fun testNotEquals() {
        val otherGroups = Groups(id = 2L, title = "Other Title")
        assertNotEquals(groups, otherGroups)
    }

    @Test
    fun testHashCode() {
        val otherGroups = Groups(id = groups.id, title = title)
        assertEquals(groups.hashCode(), otherGroups.hashCode())
    }

    @Test
    fun testToString() {
        val expectedString = "Groups(id=${groups.id}, title='$title', member=${member.id})"
        assertEquals(expectedString, groups.toString())
    }
}
