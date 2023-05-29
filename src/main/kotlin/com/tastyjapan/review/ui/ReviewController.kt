package com.tastyjapan.review.ui

import com.tastyjapan.global.response.ApiResponse
import com.tastyjapan.global.response.ApiUtils
import com.tastyjapan.review.service.ReviewService
import com.tastyjapan.review.ui.dto.BlogReviewResponse
import com.tastyjapan.review.ui.dto.ReviewRequest
import com.tastyjapan.review.ui.dto.RestaurantReviewsResponse
import com.tastyjapan.review.ui.dto.UserReviewResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
class ReviewController(val reviewService: ReviewService) {

    @PostMapping
    fun createReview(@RequestBody reviewRequest: ReviewRequest): ResponseEntity<ApiResponse<Long>> {
        val createReview = reviewService.createReview(reviewRequest)
        val apiResponse = ApiUtils.success(createReview)
        return ResponseEntity.ok(apiResponse)
    }

}