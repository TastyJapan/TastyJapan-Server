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

    @DisplayName("식당 id로 리뷰 조회에 성공한다.")
    @Test
    fun getReviewByRestaurantIdTest() {
        // given
        val review = Review(content = "맛있어요", rating = 5.0)
        restaurant.addReview(review)
        member.addReview(review)
        reviewRepository.save(review)
        // when
        val reviewList = reviewService.getReviewByRestaurantId(restaurant.id)

        // then
        assertThat(reviewList.size).isEqualTo(1)
        assertThat(reviewList[0].content).isEqualTo(review.content)
    }

    @DisplayName("식당 id로 블로그 리뷰 조회에 성공한다.")
    @Test
    fun getBlogReviewByRestaurantIdTest() {
        // given
        val blogReview = BlogReview(url = "www.naver.com", source = BlogSource.NAVER)
        restaurant.addBlogReview(blogReview)
        blogReviewRepository.save(blogReview)

        // when
        val blogReviewList = reviewService.getBlogReviewByRestaurantId(restaurant.id)

        // then
        assertThat(blogReviewList.size).isEqualTo(1)
        assertThat(blogReviewList[0].url).isEqualTo(blogReview.url)
    }

    @DisplayName("유저 id로 리뷰 조회에 성공한다.")
    @Test
    fun getReviewByUserIdTest() {
        // given
        val review = Review(content = "맛있어요", rating = 5.0)
        restaurant.addReview(review)
        member.addReview(review)
        reviewRepository.save(review)

        // when
        val reviewList = reviewService.getReviewByUserId(member.id)

        // then
        assertThat(reviewList.size).isEqualTo(1)
        assertThat(reviewList[0].content).isEqualTo(review.content)
    }

    @DisplayName("리뷰 수정에 성공한다.")
    @Test
    fun updateReviewTest() {
        // given
        val review = Review(content = "맛있어요", rating = 5.0)
        restaurant.addReview(review)
        member.addReview(review)
        reviewRepository.save(review)

        val reviewRequest = ReviewRequest(
            restaurantId = restaurant.id,
            userId = member.id,
            content = "맛없어졌어요",
            rating = 1.0
        )

        // when
        val reviewId = reviewService.updateReview(review.id, reviewRequest)

        // then
        assertThat(reviewRepository.findAll().size).isEqualTo(1)
        assertThat(reviewRepository.findById(reviewId).get().content).isEqualTo(reviewRequest.content)
    }
}