package com.tastyjapan.member.domain.repository

import com.tastyjapan.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long>, MemberRepositoryCustom {
    fun existsByEmail(email: String): Boolean
}