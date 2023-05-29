package com.tastyjapan.review.domain.repository

import com.tastyjapan.review.domain.BlogReview
import com.tastyjapan.review.domain.Review

interface BlogReviewRepositoryCustom {
    fun findReviewByRestaurantId(restaurantId: Long): List<BlogReview>
}