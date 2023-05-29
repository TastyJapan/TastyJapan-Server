package com.tastyjapan.member.domain.repository

interface MemberRepositoryCustom {
    fun checkMemberId(memberId: Long): Boolean
}