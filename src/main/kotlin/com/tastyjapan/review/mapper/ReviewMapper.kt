package com.tastyjapan.review.mapper

import com.tastyjapan.review.domain.BlogReview
import com.tastyjapan.review.domain.Review
import com.tastyjapan.review.ui.dto.BlogReviewResponse
import com.tastyjapan.review.ui.dto.RestaurantReviewsResponse
import com.tastyjapan.review.ui.dto.UserReviewResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface ReviewMapper {
    companion object {
        val INSTANCE: ReviewMapper = Mappers.getMapper(ReviewMapper::class.java)
    }

    @Mapping(source = "member", target = "user")
    fun reviewEntityToRestaurantReviews(review: Review): RestaurantReviewsResponse
    fun blogReviewEntityToBlogReviews(blogReview: BlogReview): BlogReviewResponse
}