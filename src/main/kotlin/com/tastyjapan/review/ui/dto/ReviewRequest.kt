package com.tastyjapan.review.ui.dto

import javax.persistence.Column

data class ReviewRequest(
    val userId: Long,
    val restaurantId: Long,
    val content: String,
    val rating: Double,
) {
}