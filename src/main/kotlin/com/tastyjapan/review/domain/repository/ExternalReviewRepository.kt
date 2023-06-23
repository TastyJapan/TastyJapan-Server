package com.tastyjapan.review.domain.repository

import com.tastyjapan.review.domain.ExternalReview
import org.springframework.data.jpa.repository.JpaRepository

interface ExternalReviewRepository : JpaRepository<ExternalReview, Long>, ExternalReviewRepositoryCustom {
}