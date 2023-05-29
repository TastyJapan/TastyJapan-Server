package com.tastyjapan.group.domain.repository

import com.tastyjapan.group.domain.Groups

interface GroupRepositoryCustom  {
    fun findGroupsByMemberId(memberId: Long): List<Groups>
}