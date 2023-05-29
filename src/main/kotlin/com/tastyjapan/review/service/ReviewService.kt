package com.tastyjapan.review.service

import com.tastyjapan.exception.ErrorType
import com.tastyjapan.exception.ExceptionResponse
import com.tastyjapan.exception.InvalidArgumentException
import com.tastyjapan.exception.TastyJapanException
import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.restaurant.domain.repository.RestaurantRepository
import com.tastyjapan.review.domain.Review
import com.tastyjapan.review.domain.repository.BlogReviewRepository
import com.tastyjapan.review.domain.repository.ReviewRepository
import com.tastyjapan.review.mapper.ReviewMapper
import com.tastyjapan.review.ui.dto.BlogReviewResponse
import com.tastyjapan.review.ui.dto.ReviewRequest
import com.tastyjapan.review.ui.dto.RestaurantReviewsResponse
import com.tastyjapan.review.ui.dto.UserReviewResponse
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
class ReviewService(
    val reviewRepository: ReviewRepository,
    val blogReviewRepository: BlogReviewRepository,
    val memberRepository: MemberRepository,
    val restaurantRepository: RestaurantRepository,
    val invalidArgumentException: InvalidArgumentException
) {
    @Transactional
    fun createReview(reviewRequest: ReviewRequest): Long {
        val member = memberRepository.findById(reviewRequest.userId).orElseThrow(
            { TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.USER_NOT_FOUND)) }
        )
        val restaurant = restaurantRepository.findById(reviewRequest.restaurantId).orElseThrow(
            { TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.RESTAURANT_NOT_FOUND)) }
        )
        val review = Review(
            content = reviewRequest.content, rating = reviewRequest.rating
        )
        member.addReview(review)
        restaurant.addReview(review)

        val saved = reviewRepository.save(review)
        return saved.id
    }

    fun getReviewByRestaurantId(restaurantId: Long): List<RestaurantReviewsResponse> {
        return reviewRepository.findReviewByRestaurantId(restaurantId).stream()
            .map { review -> ReviewMapper.INSTANCE.reviewEntityToRestaurantReviews(review) }
            .collect(Collectors.toList())
    }

    fun getBlogReviewByRestaurantId(restaurantId: Long): List<BlogReviewResponse> {
        return blogReviewRepository.findReviewByRestaurantId(restaurantId).stream()
            .map { review -> ReviewMapper.INSTANCE.blogReviewEntityToBlogReviews(review) }
            .collect(Collectors.toList())
    }

}