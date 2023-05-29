package com.tastyjapan.review.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tastyjapan.member.domain.QMember
import com.tastyjapan.review.domain.Review
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import com.tastyjapan.review.domain.QReview.review

@Repository
class ReviewRepositoryImpl(entityManager: EntityManager) : ReviewRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findReviewByRestaurantId(restaurantId: Long): List<Review> {
        return queryFactory.selectFrom(review)
            .where(review.restaurant.id.eq(restaurantId).and(review.isDeleted.isFalse))
            .fetch()
    }

}