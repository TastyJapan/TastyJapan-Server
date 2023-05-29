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

    override fun findReviewByMemberId(memberId: Long): List<Review> {
        return queryFactory.selectFrom(review)
            .where(review.member.id.eq(memberId).and(review.isDeleted.isFalse))
            .fetch()
    }

    override fun updateReview(reviewId: Long, content: String, rating: Double): Long {
        return queryFactory.update(review)
            .where(review.id.eq(reviewId))
            .set(review.content, content)
            .set(review.rating, rating)
            .execute()
    }

    override fun deleteReview(reviewId: Long): Long {
        return queryFactory.update(review)
            .where(review.id.eq(reviewId))
            .set(review.isDeleted, true)
            .execute()
    }
    override fun checkReviewId(reviewId: Long): Boolean {
        return queryFactory.selectOne()
            .from(review)
            .where(review.id.eq(reviewId).and(review.isDeleted.isFalse))
            .fetchFirst() != null
    }
}