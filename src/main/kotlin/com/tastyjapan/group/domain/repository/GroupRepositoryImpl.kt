package com.tastyjapan.group.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tastyjapan.group.domain.Groups
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

import com.tastyjapan.group.domain.QGroups.groups

@Repository
class GroupRepositoryImpl(entityManager: EntityManager) : GroupRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

}