package com.tastyjapan.review.service

import com.tastyjapan.fixture.MemberFixture.createMemberChoco
import com.tastyjapan.fixture.RestaurantFixture.createRestaurantRamen
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.restaurant.domain.Restaurant
import com.tastyjapan.restaurant.domain.repository.RestaurantRepository
import com.tastyjapan.review.domain.BlogReview
import com.tastyjapan.review.domain.BlogSource
import com.tastyjapan.review.domain.Review
import com.tastyjapan.review.domain.repository.BlogReviewRepository
import com.tastyjapan.review.domain.repository.ReviewRepository
import com.tastyjapan.review.ui.dto.ReviewRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private lateinit var reviewService: ReviewService

    @Autowired
    private lateinit var reviewRepository: ReviewRepository

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Autowired
    private lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    private lateinit var blogReviewRepository: BlogReviewRepository

    private lateinit var member: Member
    private lateinit var restaurant: Restaurant

    @BeforeEach
    fun setUp() {
        reviewRepository.deleteAll()
        memberRepository.deleteAll()
        restaurantRepository.deleteAll()
        blogReviewRepository.deleteAll()

        member = createMemberChoco()
        memberRepository.save(member)
        restaurant = createRestaurantRamen()
        restaurantRepository.save(restaurant)
    }

    @DisplayName("모든 리뷰 조회에 성공한다.")
    @Test
    fun createReviewTest() {
        //given
        val reviewRequest = ReviewRequest(
            restaurantId = restaurant.id,
            userId = member.id,
            content = "맛있어요",
            rating = 5.0
        )

        //when
        val reviewId = reviewService.createReview(reviewRequest)

        //then
        assertThat(reviewRepository.findAll().size).isEqualTo(1)
        assertThat(reviewRepository.findById(reviewId).get().content).isEqualTo(reviewRequest.content)
    }
}