package com.tastyjapan.group.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tastyjapan.group.domain.Groups
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

import com.tastyjapan.group.domain.QGroups.groups

@Repository
class GroupRepositoryImpl(entityManager: EntityManager) : GroupRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findGroupsByMemberId(memberId: Long): List<Groups> {
        return queryFactory
            .select(groups)
            .from(groups)
            .where(groups.member.id.eq(memberId)).fetch()
    }

    override fun updateGroupTitle(groupId: Long, title: String): Long {
        return queryFactory.update(groups)
            .set(groups.title, title)
            .where(groups.id.eq(groupId))
            .execute()
    }
}