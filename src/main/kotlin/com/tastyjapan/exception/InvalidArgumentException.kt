package com.tastyjapan.exception

import com.tastyjapan.group.domain.repository.GroupRepository
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.restaurant.domain.repository.RestaurantRepository
import com.tastyjapan.review.domain.repository.ReviewRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class InvalidArgumentException(
    private val memberRepository: MemberRepository,
    private val restaurantRepository: RestaurantRepository,
    private val reviewRepository: ReviewRepository,
    private val groupRepository: GroupRepository
) {
    fun checkMemberId(memberId: Long, member: Member) {
        try {
            memberRepository.checkMemberId(memberId)
            if (memberId != member.id) {
                throw TastyJapanException(
                    HttpStatus.FORBIDDEN,
                    ExceptionResponse(ErrorType.NOT_ALLOWED_PERMISSION_ERROR)
                )
            }
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

    fun checkReview(reviewId: Long, member: Member) {
        try {
            val review = reviewRepository.findById(reviewId).get()

            if (review.member?.id != member.id) {
                throw TastyJapanException(
                    HttpStatus.FORBIDDEN,
                    ExceptionResponse(ErrorType.NOT_ALLOWED_PERMISSION_ERROR)
                )
            }
        } catch (e: Exception) {
            throw TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.REVIEW_NOT_FOUND))
        }
    }

    fun checkGroup(groupId: Long, member:Member){
        try{
            val group = groupRepository.findById(groupId).get()
            if (group.member?.id != member.id) {
                throw TastyJapanException(
                    HttpStatus.FORBIDDEN,
                    ExceptionResponse(ErrorType.NOT_ALLOWED_PERMISSION_ERROR)
                )
            }
        }catch (e:Exception){
            throw TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.GROUP_NOT_FOUND))
        }
    }

}