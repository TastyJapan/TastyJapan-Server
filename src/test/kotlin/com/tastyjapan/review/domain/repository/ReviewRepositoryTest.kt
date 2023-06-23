package com.tastyjapan.review.domain.repository

import com.tastyjapan.review.domain.Review
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    lateinit var reviewRepository: ReviewRepository

    lateinit var review: Review

    @BeforeEach
    fun setUp() {
        reviewRepository.deleteAll()
        review = Review(content = "review1", rating = 5.00)
        reviewRepository.save(review)
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 메뉴를 저장하면, 해당 ID의 메뉴는 후에 작성된 메뉴 정보로 업데이트 된다.")
    @Test
    fun saveSameId() {
        // given
        val review2 = Review(id = review.id ,content = "review2", rating = 4.00)

        // when
        reviewRepository.save(review2)

        // then
        assertThat(reviewRepository.findById(review.id).get().content).isEqualTo(review2.content)
    }

    @DisplayName("id가 없는 Menu 엔티티를 저장하면 순차적으로 ID를 부여하여 저장한다.")
    @Test
    fun saveNoIdGroup() {
        // given
        val review2 = Review(content = "review2", rating = 4.00)
        reviewRepository.save(review2)

        // when&then
        val idDiff = review2.id - review.id
        assertThat(idDiff).isEqualTo(1L);
    }
}