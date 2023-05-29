package com.tastyjapan.review.domain.repository

import com.tastyjapan.review.domain.ExternalReview

interface ExternalReviewRepositoryCustom {
    fun findExternalReviewByRestaurantId(restaurantId: Long): List<ExternalReview>
}