package com.tastyjapan.review.ui

import com.tastyjapan.global.annotation.CurrentMember
import com.tastyjapan.global.response.ApiResponse
import com.tastyjapan.global.response.ApiUtils
import com.tastyjapan.member.domain.Member
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
@RequestMapping("/api/v1/reviews")
class ReviewController(val reviewService: ReviewService) {

    @PostMapping
    fun createReview(@RequestBody reviewRequest: ReviewRequest): ResponseEntity<ApiResponse<Long>> {
        val createReview = reviewService.createReview(reviewRequest)
        val apiResponse = ApiUtils.success(createReview)
        return ResponseEntity.ok(apiResponse)
    }

    @GetMapping("/restaurants/{restaurant-id}")
    fun getReviewsByRestaurant(
        @PathVariable("restaurant-id") restaurantId: Long
    ): ResponseEntity<ApiResponse<List<RestaurantReviewsResponse>>> {
        val result = reviewService.getReviewByRestaurantId(restaurantId)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    @GetMapping("/restaurants/{restaurant-id}/blogs")
    fun getBlogReviewsByRestaurant(
        @PathVariable("restaurant-id") restaurantId: Long
    ): ResponseEntity<ApiResponse<List<BlogReviewResponse>>> {
        val result = reviewService.getBlogReviewByRestaurantId(restaurantId)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    @GetMapping("/")
    fun getUserReviews(
        @CurrentMember member: Member,
    ): ResponseEntity<ApiResponse<List<UserReviewResponse>>> {
        val result = reviewService.getReviewByUserId(member.id)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    @PutMapping("/{review-id}")
    fun updateReview(
        @CurrentMember member: Member,
        @PathVariable("review-id") reviewId: Long,
        @RequestBody reviewRequest: ReviewRequest
    ): ResponseEntity<ApiResponse<Long>> {
        val result = reviewService.updateReview(reviewId, reviewRequest, member)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    @DeleteMapping("/{review-id}")
    fun deleteReview(
        @CurrentMember member: Member,
        @PathVariable("review-id") reviewId: Long
    ): ResponseEntity<ApiResponse<Boolean>> {
        val result = reviewService.deleteReview(reviewId, member)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }
}