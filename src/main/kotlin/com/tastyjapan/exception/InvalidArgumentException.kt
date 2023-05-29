package com.tastyjapan.exception

import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.restaurant.domain.repository.RestaurantRepository
import com.tastyjapan.review.domain.repository.ReviewRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class InvalidArgumentException(
    private val memberRepository: MemberRepository,
    private val restaurantRepository: RestaurantRepository,
    private val reviewRepository: ReviewRepository
) {
    fun checkMemberId(memberId: Long) {
        try {
            memberRepository.checkMemberId(memberId)
        } catch (e: Exception) {
            throw TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.USER_NOT_FOUND))
        }
    }

    fun checkRestaurantId(restaurantId: Long) {
        try {
            restaurantRepository.checkRestaurantId(restaurantId)
        } catch (e: Exception) {
            throw TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.RESTAURANT_NOT_FOUND))
        }
    }

}