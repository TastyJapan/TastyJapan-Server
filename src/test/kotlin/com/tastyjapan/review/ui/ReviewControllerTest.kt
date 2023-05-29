import com.tastyjapan.fixture.MemberFixture.createMemberChoco
import com.tastyjapan.global.response.ApiUtils
import com.tastyjapan.member.mapper.MemberMapper
import com.tastyjapan.review.domain.BlogSource
import com.tastyjapan.review.service.ReviewService
import com.tastyjapan.review.ui.ReviewController
import com.tastyjapan.review.ui.dto.BlogReviewResponse
import com.tastyjapan.review.ui.dto.ReviewRequest
import com.tastyjapan.review.ui.dto.RestaurantReviewsResponse
import com.tastyjapan.review.ui.dto.UserReviewResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity


class ReviewControllerTest {

    @Test
    fun createReview() {
        val reviewService = mockk<ReviewService>()
        val reviewController = ReviewController(reviewService)

        val reviewRequest = ReviewRequest(userId = 123L, restaurantId = 456L, rating = 4.5, content = "Great food!")

        val expectedReviewId = 789L
        val expectedResponse = ResponseEntity.ok(ApiUtils.success(expectedReviewId))

        every {
            reviewService.createReview(reviewRequest)
        } returns expectedReviewId

        val response = reviewController.createReview(reviewRequest)

        assertEquals(expectedResponse.statusCode, response.statusCode)
        assertEquals(expectedResponse.body?.getResponse(), response.body?.getResponse())
    }

    @Test
    fun getReviewsByRestaurant() {
        val reviewService = mockk<ReviewService>()
        val reviewController = ReviewController(reviewService)
        val restaurantId = 456L

        val expectedRestaurantReviewsResponse = listOf(
            RestaurantReviewsResponse(user = MemberMapper.INSTANCE.memberEntityToResponse(createMemberChoco()), rating = 4.0, content = "Good food"),
            RestaurantReviewsResponse(user = MemberMapper.INSTANCE.memberEntityToResponse(createMemberChoco()), rating = 3.5, content = "Average experience")
        )
        val expectedResponse = ResponseEntity.ok(ApiUtils.success(expectedRestaurantReviewsResponse))

        every {
            reviewService.getReviewByRestaurantId(restaurantId)
        } returns expectedRestaurantReviewsResponse

        val response = reviewController.getReviewsByRestaurant(restaurantId)

        assertEquals(expectedResponse.statusCode, response.statusCode)
        assertEquals(expectedResponse.body?.getResponse(), response.body?.getResponse())
    }

    @Test
    fun getBlogReviewsByRestaurant() {
        val reviewService = mockk<ReviewService>()
        val reviewController = ReviewController(reviewService)
        val restaurantId = 456L

        val expectedBlogReviewsResponse = listOf(
            BlogReviewResponse(url = "https://www.google.com", source = BlogSource.ETC),
            BlogReviewResponse(url = "https://www.google.com", source = BlogSource.ETC),
        )
        val expectedResponse = ResponseEntity.ok(ApiUtils.success(expectedBlogReviewsResponse))

        every {
            reviewService.getBlogReviewByRestaurantId(restaurantId)
        } returns expectedBlogReviewsResponse

        val response = reviewController.getBlogReviewsByRestaurant(restaurantId)

        assertEquals(expectedResponse.statusCode, response.statusCode)
        assertEquals(expectedResponse.body?.getResponse(), response.body?.getResponse())
    }

    @Test
    fun getUserReviews() {
        val reviewService = mockk<ReviewService>()
        val reviewController = ReviewController(reviewService)
        val userId = 123L

        val expectedUserReviewsResponse = listOf(
            UserReviewResponse(rating = 4.0, content = "Good food"),
            UserReviewResponse(rating = 3.5, content = "Average experience")
        )
        val expectedResponse = ResponseEntity.ok(ApiUtils.success(expectedUserReviewsResponse))

        every {
            reviewService.getReviewByUserId(userId)
        } returns expectedUserReviewsResponse

        val response = reviewController.getUserReviews(userId)

        assertEquals(expectedResponse.statusCode, response.statusCode)
        assertEquals(expectedResponse.body?.getResponse(), response.body?.getResponse())
    }

    @Test
    fun updateReview() {
        val reviewService = mockk<ReviewService>()
        val reviewController = ReviewController(reviewService)
        val reviewId = 123L

        val reviewRequest = ReviewRequest(userId = 456L, restaurantId = 789L, rating = 4.5, content = "Updated review")

        val expectedUpdatedReviewId = 123L
        val expectedResponse = ResponseEntity.ok(ApiUtils.success(expectedUpdatedReviewId))

        every {
            reviewService.updateReview(reviewId, reviewRequest)
        } returns expectedUpdatedReviewId

        val response = reviewController.updateReview(reviewId, reviewRequest)

        assertEquals(expectedResponse.statusCode, response.statusCode)
        assertEquals(expectedResponse.body?.getResponse(), response.body?.getResponse())
    }

    @Test
    fun deleteReview() {
        val reviewService = mockk<ReviewService>()
        val reviewController = ReviewController(reviewService)
        val reviewId = 123L

        val expectedDeleted = true
        val expectedResponse = ResponseEntity.ok(ApiUtils.success(expectedDeleted))

        every {
            reviewService.deleteReview(reviewId)
        } returns expectedDeleted

        val response = reviewController.deleteReview(reviewId)

        assertEquals(expectedResponse.statusCode, response.statusCode)
        assertEquals(expectedResponse.body?.getResponse(), response.body?.getResponse())
    }
}
