package com.tastyjapan.fixture

import com.tastyjapan.group.domain.Groups

object GroupFixture {
    fun createGroup1(): Groups {
        return Groups(title = "group1")
    }
}