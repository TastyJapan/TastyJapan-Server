package com.tastyjapan.fixture

import com.tastyjapan.review.domain.Review

object ReviewFixture {
    fun createRamenReview(): Review {
        return Review(
            content = "내 최애 도쿄 라멘 맛집!",
            rating = 4.8
        )
    }
}