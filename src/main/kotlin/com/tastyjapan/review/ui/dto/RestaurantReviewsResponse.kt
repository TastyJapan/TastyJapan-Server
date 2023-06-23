package com.tastyjapan.review.ui.dto

import com.tastyjapan.member.ui.dto.MemberResponse

data class RestaurantReviewsResponse(
    val user: MemberResponse,
    val content: String,
    val rating: Double,
) {
}