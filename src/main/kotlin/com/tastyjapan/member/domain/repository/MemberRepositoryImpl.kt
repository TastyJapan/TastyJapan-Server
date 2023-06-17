package com.tastyjapan.member.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tastyjapan.member.domain.Member
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import com.tastyjapan.member.domain.QMember.member

@Repository
class MemberRepositoryImpl(entityManager: EntityManager) : MemberRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun checkMemberId(memberId: Long): Boolean {
        return queryFactory.selectOne()
            .from(member)
            .where(member.id.eq(memberId).and(member.isDeleted.isFalse))
            .fetchFirst() != null
    }

    override fun findByEmail(email: String): Member? {
        return queryFactory.selectFrom(member)
            .where(member.email.eq(email).and(member.isDeleted.isFalse))
            .fetchOne()
    }

}