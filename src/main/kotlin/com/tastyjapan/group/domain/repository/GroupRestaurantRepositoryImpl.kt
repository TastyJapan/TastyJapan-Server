package com.tastyjapan.group.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tastyjapan.group.domain.GroupRestaurant
import com.tastyjapan.group.domain.QGroupRestaurant.groupRestaurant
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager


@Repository
class GroupRestaurantRepositoryImpl(entityManager: EntityManager): GroupRestaurantRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findGroupRestaurantByGroupId(groupId: Long): List<GroupRestaurant> {
        return queryFactory.selectFrom(groupRestaurant)
                .where(groupRestaurant.groups.id.eq(groupId))
                .fetch()
    }
}