package com.tastyjapan.review.domain.repository

import com.tastyjapan.review.domain.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long>, ReviewRepositoryCustom {
}