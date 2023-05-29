package com.tastyjapan.member.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import com.tastyjapan.member.domain.QMember.member

@Repository
class MemberRepositoryImpl(entityManager: EntityManager) : MemberRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)
}