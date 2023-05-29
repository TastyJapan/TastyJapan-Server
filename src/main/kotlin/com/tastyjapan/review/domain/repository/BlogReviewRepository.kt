package com.tastyjapan.review.domain.repository

import com.tastyjapan.review.domain.BlogReview
import org.springframework.data.jpa.repository.JpaRepository

interface BlogReviewRepository : JpaRepository<BlogReview, Long>, BlogReviewRepositoryCustom{
}