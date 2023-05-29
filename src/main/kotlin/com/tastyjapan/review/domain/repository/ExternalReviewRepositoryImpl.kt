package com.tastyjapan.review.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tastyjapan.review.domain.ExternalReview
import javax.persistence.EntityManager
import com.tastyjapan.review.domain.QExternalReview.externalReview


class ExternalReviewRepositoryImpl(entityManager: EntityManager) : ExternalReviewRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findExternalReviewByRestaurantId(restaurantId: Long): List<ExternalReview> {
        return queryFactory.selectFrom(externalReview)
            .where(externalReview.restaurant.id.eq(restaurantId).and(externalReview.isDeleted.isFalse))
            .fetch()
    }
}