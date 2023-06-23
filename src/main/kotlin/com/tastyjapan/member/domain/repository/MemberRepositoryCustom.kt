package com.tastyjapan.member.domain.repository

import com.tastyjapan.member.domain.Member

interface MemberRepositoryCustom {
    fun checkMemberId(memberId: Long): Boolean
    fun findByEmail(email: String): Member?
}