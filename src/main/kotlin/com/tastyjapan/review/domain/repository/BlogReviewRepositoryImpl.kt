package com.tastyjapan.review.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tastyjapan.review.domain.BlogReview
import javax.persistence.EntityManager
import com.tastyjapan.review.domain.QBlogReview.blogReview

class BlogReviewRepositoryImpl(entityManager: EntityManager) : BlogReviewRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findReviewByRestaurantId(restaurantId: Long): List<BlogReview> {
        return queryFactory.selectFrom(blogReview)
            .where(blogReview.restaurant.id.eq(restaurantId).and(blogReview.isDeleted.isFalse))
            .fetch()
    }
}