package com.tastyjapan.review.domain.repository

import com.tastyjapan.review.domain.Review

interface ReviewRepositoryCustom {
    fun findReviewByRestaurantId(restaurantId: Long): List<Review>
    fun findReviewByMemberId(memberId: Long): List<Review>
}